package com.estacionvital.patienthubestacionvital.presenter.implementations

import android.content.Context
import com.estacionvital.patienthubestacionvital.data.local.SharedPrefManager
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IEVRetrieveUserExaminationsHIstoryCalllback
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IEVTwilioCallSubscribedChannelsCallBack
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IEVTwilioClientCallback
import com.estacionvital.patienthubestacionvital.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.*
import com.estacionvital.patienthubestacionvital.presenter.IConversationHistoryPresenter
import com.estacionvital.patienthubestacionvital.ui.fragmentViews.IConversationHistoryFragmentView
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
                    newChannel.status = channel.finished
                    newChannel.type = channel.service_type
                    newChannel.unique_name = channel.channel_name
                    newChannel.specialty = channel.specialty

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

}