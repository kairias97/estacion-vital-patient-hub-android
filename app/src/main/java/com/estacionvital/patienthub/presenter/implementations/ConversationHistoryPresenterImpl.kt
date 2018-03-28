package com.estacionvital.patienthub.presenter.implementations

import android.content.Context
import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveUserExaminationsHIstoryCalllback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioCallSubscribedChannelsCallBack
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioClientCallback
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.IConversationHistoryPresenter
import com.estacionvital.patienthub.ui.fragmentViews.IConversationHistoryFragmentView
import com.estacionvital.patienthub.util.CHAT_FREE
import com.estacionvital.patienthub.util.CHAT_PREMIUM
import com.twilio.chat.Channel

/**
 * Created by dusti on 16/03/2018.
 */
class ConversationHistoryPresenterImpl: IConversationHistoryPresenter {
    override fun expireSession() {
        mSharedPrefManager.clearPreferences()
        mConverstaionHistoryFragmentView.showExpirationMessage()
    }

    private val mConverstaionHistoryFragmentView: IConversationHistoryFragmentView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource
    private val mSharedPrefManager:SharedPrefManager

    constructor(sharedPrefManager: SharedPrefManager ,conversationHistoryFragmentView: IConversationHistoryFragmentView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource, evTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource){
        this.mConverstaionHistoryFragmentView = conversationHistoryFragmentView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun retrieveConversationHistory(context: Context) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mConverstaionHistoryFragmentView.showLoadingProgress()
        mEstacionVitalRemoteDataSource.retrieveExaminationsHistory(token, object: IEVRetrieveUserExaminationsHIstoryCalllback{
            override fun onTokenExpired() {
                expireSession()
            }

            override fun onSuccess(response: EVRetrieveUserExaminationResponse) {
                mConverstaionHistoryFragmentView.setHistory(response.data)
            }

            override fun onFailure() {
                mConverstaionHistoryFragmentView.hideLoading()
                mConverstaionHistoryFragmentView.showError()
            }
        })
    }
    override fun setUpTwilioClient(context: Context, data: List<EVUserExamination>) {
        mEVTwilioChatRemoteDataSource.setupTwilioClient(EVUserSession.instance.twilioToken,context,object: IEVTwilioClientCallback{
            override fun onSuccess() {
                callSubscribedChannels(data)
            }

            override fun onFailure() {
                mConverstaionHistoryFragmentView.hideLoading()
                mConverstaionHistoryFragmentView.showError()
            }
        })
    }

    override fun callSubscribedChannels(data: List<EVUserExamination>) {
        mEVTwilioChatRemoteDataSource.callSubscribedChannels(object: IEVTwilioCallSubscribedChannelsCallBack{
            override fun onSuccess(channels: List<Channel>) {
                var list: MutableList<EVChannel> = ArrayList<EVChannel>()
                for(channel in data){
                    var newChannel = EVChannel()
                    newChannel.isFinished = channel.finished
                    newChannel.type = channel.service_type
                    newChannel.unique_name = channel.channel_name
                    newChannel.specialty = channel.specialty
                    newChannel.doctorName = channel.doctorName

                    for(twilioChannel in channels){
                        if(twilioChannel.uniqueName.toString() == channel.channel_name){
                            newChannel.twilioChannel = twilioChannel
                        }
                    }
                    if(newChannel.twilioChannel != null){
                        list.add(newChannel)
                    }
                }
                mConverstaionHistoryFragmentView.setChannelList(list)
            }

            override fun onFailure() {
                mConverstaionHistoryFragmentView.hideLoading()
                mConverstaionHistoryFragmentView.showError()
            }
        })
    }

    override fun filterByTypeChat(data: MutableList<EVChannel>, type: String): MutableList<EVChannel> {
        var list: MutableList<EVChannel> = ArrayList<EVChannel>()
        if(type == CHAT_FREE){
            for(channel in data.reversed()){
                if(channel.type == CHAT_FREE){
                    list.add(channel)
                    break
                }
            }
            return list
        }
        else if(type == CHAT_PREMIUM){
            for(channel in data.reversed()){
                if(channel.type == CHAT_PREMIUM){
                    list.add(channel)
                }

            }
            return list
        }
        return list
    }

}