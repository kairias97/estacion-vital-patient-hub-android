package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.*
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.ITwilioChatPresenter
import com.estacionvital.patienthub.ui.views.ITwilioChatView
import com.estacionvital.patienthub.util.CHAT_FREE
import com.estacionvital.patienthub.util.CHAT_PAID
import com.twilio.chat.CallbackListener
import com.twilio.chat.Channel
import com.twilio.chat.Message

/**
 * Created by dusti on 20/03/2018.
 */
class TwilioChatPresenterImpl: ITwilioChatPresenter {
    /*
    override fun setupChatChannel(channelID: String) {

        //Setup twilio token here.
        //Load examination data
        //After loading examination data, make EVChannel and then process it
        val token = "Token token=${EVUserSession.instance.authToken}"
        //Load channel info here

        mEstacionVitalRemoteDataSource.getChannelByID(token, EVGetChannelByIDRequest(channelID), object: IGetChannelByUniqueName {
            override fun onSuccess(response: EVUserExaminationByIDResponse) {
                val evChannel = EVChannel()
                evChannel.type = response.data.serviceType
                evChannel.specialty = response.data.specialty
                evChannel.isFinished = response.data.finished
                evChannel.unique_name = response.data.channel_name
                evChannel.doctorName = response.data.doctorName
                setupChatChannel(evChannel)

            }

            override fun onFailure() {
                mTwilioChatView.showErrorLoading()
            }

            override fun onTokenExpired() {
                //Expire session here
                //Meanwhile just show a generic error loading error
                mTwilioChatView.showErrorLoading()
            }

        } )

    }

*/
    override fun onMessageTextChanged(msg: String) {
        if(msg.isNullOrEmpty()){
            mTwilioChatView.disableSendButton()
        }
        else{
            mTwilioChatView.enableSendButton()
        }

    }

    private val mTwilioChatView: ITwilioChatView
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    constructor(twilioChatView: ITwilioChatView, evTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource,
                evRemoteDataSource: EstacionVitalRemoteDataSource){
        this.mTwilioChatView = twilioChatView
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDataSource
        this.mEstacionVitalRemoteDataSource = evRemoteDataSource
    }
    override fun setupChatChannel(channel: EVChannel) {
        if (channel.type == CHAT_FREE) {
            mTwilioChatView.showFreeChatBanner()
        }
        validateChannelStatus(channel)
        retrieveMessages(channel)
    }
    private fun validateChannelStatus(channel: EVChannel) {

        mTwilioChatView.disableMessageTextInput()
        mTwilioChatView.disableSendButton()
        mTwilioChatView.hideMessagingControls()
        if (!channel.isFinished && channel.type == CHAT_PAID ){
            //mTwilioChatView.disableSendButton()
            mTwilioChatView.showMessagingControls()
            mTwilioChatView.bindMessageTextInputListener()
            mTwilioChatView.enableMessageTextInput()

        } else if (!channel.isFinished && channel.type == CHAT_FREE) {
            //This was commented because it is never binded via activity
            //mTwilioChatView.unbindMessageTextInputListener()
            //mTwilioChatView.disableMessageTextInput()
            channel.twilioChannel!!.getMessagesCount(object: CallbackListener<Long>() {
                override fun onSuccess(count: Long?) {
                    if (count!! == 0.toLong()) {
                        mTwilioChatView.showMessagingControls()
                        mTwilioChatView.bindMessageTextInputListener()
                        mTwilioChatView.enableMessageTextInput()
                    }
                }

            })
        }
    }

    private fun retrieveMessages(channel: EVChannel) {
        mTwilioChatView.showMessageLoading()
        if(EVChatSession.instance.isChatClientCreated){
            mEVTwilioChatRemoteDataSource.getLastMessagesFromChannel(channel.twilioChannel!!, object: IEVTwilioGetLastMessagesFromChannelCalBack{
                override fun onSuccess(messages: List<Message>) {
                    mTwilioChatView.hideLoading()
                    var list: MutableList<Message> = ArrayList<Message>()
                    for(message in messages){
                        list.add(message)
                    }
                    mTwilioChatView.setChannelMessagesUI(list)
                    refreshChannel(channel)
                }

                override fun onFailure() {
                    mTwilioChatView.hideLoading()
                    mTwilioChatView.showErrorLoading()
                }
            })
        }
    }

    private fun refreshChannel(evChannel: EVChannel) {
        if(EVChatSession.instance.isChatClientCreated){
            mEVTwilioChatRemoteDataSource.findChannelByID(evChannel.unique_name, object: IEVTwilioFindChannelByIDCallback{
                override fun onSuccess(channel: Channel) {
                    evChannel.twilioChannel = channel
                    setChannelListeners(channel)
                }

                override fun onFailure() {

                }

            })
            


        }
    }

    private fun setChannelListeners(channel: Channel) {
        mEVTwilioChatRemoteDataSource.subscribeToAddedMessages(channel, object: IEVTwilioMessageAddedCallBack{
            override fun onSuccess(message: Message) {
                mTwilioChatView.addMessageToUI(message)
            }

            override fun onFailure() {
                mTwilioChatView.showErrorLoading()
            }
        }, object: IEVMemberAddedCallBack{
            override fun onSuccess(name: String) {
                mTwilioChatView.showDoctorJoined(name)
            }

            override fun onFailure() {
                mTwilioChatView.showErrorLoading()
            }
        }, object: IEVMemberDeletedCallBack{
            override fun onSuccess() {
                mTwilioChatView.disableMessageTextInput()
                mTwilioChatView.disableSendButton()
                mTwilioChatView.showDoctorLeaved()

            }

            override fun onFailure() {
                mTwilioChatView.showErrorLoading()
            }
        })
    }

    override fun sendMessage(channel: EVChannel, body: String) {
        if(EVChatSession.instance.isChatClientCreated){
            mEVTwilioChatRemoteDataSource.sendMessage(channel.twilioChannel!!,body,object: IEVTwilioSendMessageCallBack{
                override fun onSuccess() {
                    if (channel.type == CHAT_FREE) {
                        mTwilioChatView.unbindMessageTextInputListener()
                        mTwilioChatView.disableMessageTextInput()
                        mTwilioChatView.disableSendButton()
                        mTwilioChatView.hideMessagingControls()
                        mTwilioChatView.showFreeChatDisabledMessage()
                    }
                }

                override fun onFailure() {

                    mTwilioChatView.showErrorSendingMessage()
                }
            })
        }
    }

}