package com.estacionvital.patienthubestacionvital.presenter.implementations

import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IEVTwilioFindChannelByIDCallback
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IEVTwilioGetLastMessagesFromChannelCalBack
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IEVTwilioMessageAddedCallBack
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IEVTwilioSendMessageCallBack
import com.estacionvital.patienthubestacionvital.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.EVChatSession
import com.estacionvital.patienthubestacionvital.presenter.ITwilioChatrPresenter
import com.estacionvital.patienthubestacionvital.ui.views.ITwilioChatView
import com.twilio.chat.Channel
import com.twilio.chat.Message

/**
 * Created by dusti on 20/03/2018.
 */
class TwilioChatPresenterImpl: ITwilioChatrPresenter {
    private val mTwilioChatView: ITwilioChatView
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource

    constructor(twilioChatView: ITwilioChatView, evTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource){
        this.mTwilioChatView = twilioChatView
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDataSource
    }

    override fun retrieveChannel(roomID: String) {
        if(EVChatSession.instance.isChatClientCreated == true){
            mEVTwilioChatRemoteDataSource.findChannelByID(roomID, object: IEVTwilioFindChannelByIDCallback{
                override fun onSuccess(channel: Channel) {
                    mTwilioChatView.getChannelFromID(channel)
                }

                override fun onFailure() {
                    mTwilioChatView.hideLoading()
                    mTwilioChatView.showErrorLoading()
                }
            })
        }
    }

    override fun retrieveMessages(channel: Channel) {
        if(EVChatSession.instance.isChatClientCreated == true){
            mEVTwilioChatRemoteDataSource.getLastMessagesFromChannel(channel, object: IEVTwilioGetLastMessagesFromChannelCalBack{
                override fun onSuccess(messages: List<Message>) {
                    var list: MutableList<Message> = ArrayList<Message>()
                    for(message in messages){
                        list.add(message)
                    }
                    mTwilioChatView.getMessagesFromChannel(list)
                }

                override fun onFailure() {
                    mTwilioChatView.hideLoading()
                    mTwilioChatView.showErrorLoading()
                }
            })
        }
    }

    override fun setChannelListener(channel: Channel) {
        if(EVChatSession.instance.isChatClientCreated){
            mEVTwilioChatRemoteDataSource.subscribeToAddedMessages(channel, object: IEVTwilioMessageAddedCallBack{
                override fun onSuccess(message: Message) {
                    mTwilioChatView.getNewMessage(message)
                }

                override fun onFailure() {
                    mTwilioChatView.showErrorLoading()
                }
            })
        }
    }

    override fun sendMessage(channel: Channel, body: String) {
        if(EVChatSession.instance.isChatClientCreated){
            mEVTwilioChatRemoteDataSource.sendMesage(channel,body,object: IEVTwilioSendMessageCallBack{
                override fun onSuccess() {

                }

                override fun onFailure() {

                    mTwilioChatView.showErrorLoading()
                }
            })
        }
    }

}