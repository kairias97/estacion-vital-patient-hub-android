package com.estacionvital.patienthub.presenter.implementations

import android.content.Context
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveUserExaminationsHIstoryCalllback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioCallSubscribedChannelsCallBack
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioClientCallback
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.IConversationHistoryPresenter
import com.estacionvital.patienthub.ui.fragmentViews.IConversationHistoryFragmentView
import com.twilio.chat.Channel

/**
 * Created by dusti on 16/03/2018.
 */
class ConversationHistoryPresenterImpl: IConversationHistoryPresenter {

    private val mConverstaionHistoryFragmentView: IConversationHistoryFragmentView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource

    constructor(conversationHistoryFragmentView: IConversationHistoryFragmentView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource, evTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource){
        this.mConverstaionHistoryFragmentView = conversationHistoryFragmentView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDataSource
    }

    override fun retrieveConversationHistory(context: Context) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mConverstaionHistoryFragmentView.showLoadingProgress()
        mEstacionVitalRemoteDataSource.retrieveExaminationsHistory(token, object: IEVRetrieveUserExaminationsHIstoryCalllback{
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
                mConverstaionHistoryFragmentView.hideLoading()
                mConverstaionHistoryFragmentView.getChannels(channels)
            }

            override fun onFailure() {
                mConverstaionHistoryFragmentView.hideLoading()
                mConverstaionHistoryFragmentView.showError()
            }
        })
    }

}