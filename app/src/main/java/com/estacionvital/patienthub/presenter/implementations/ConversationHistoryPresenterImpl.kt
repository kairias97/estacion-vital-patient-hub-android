package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveUserExaminationsHIstoryCalllback
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVRetrieveUserExaminationResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.IConversationHistoryPresenter
import com.estacionvital.patienthub.ui.fragmentViews.IConversationHistoryFragmentView

/**
 * Created by dusti on 16/03/2018.
 */
class ConversationHistoryPresenterImpl: IConversationHistoryPresenter {

    private val mConverstaionHistoryFragmentView: IConversationHistoryFragmentView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    constructor(conversationHistoryFragmentView: IConversationHistoryFragmentView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource){
        this.mConverstaionHistoryFragmentView = conversationHistoryFragmentView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
    }

    override fun retrieveConversationHistory() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mConverstaionHistoryFragmentView.showLoadingProgress()
        mEstacionVitalRemoteDataSource.retrieveExaminationsHistory(token, object: IEVRetrieveUserExaminationsHIstoryCalllback{
            override fun onSuccess(response: EVRetrieveUserExaminationResponse) {
                mConverstaionHistoryFragmentView.hideLoading()
                mConverstaionHistoryFragmentView.setHistory(response)
            }

            override fun onFailure() {
                mConverstaionHistoryFragmentView.hideLoading()
                mConverstaionHistoryFragmentView.showError()
            }
        })
    }

}