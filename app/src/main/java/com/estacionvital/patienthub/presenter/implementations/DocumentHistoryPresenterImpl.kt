package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.IGetDocumentsCallback
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.DocumentsResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.IDocumentHistoryPresenter
import com.estacionvital.patienthub.ui.fragmentViews.IDocumentHistoryFragmentView
import com.estacionvital.patienthub.util.CHAT_PREMIUM

/**
 * Created by kevin on 24/3/2018.
 */
class DocumentHistoryPresenterImpl: IDocumentHistoryPresenter {
    override fun expireSession() {
        mDocumentHistoryView.showExpirationMessage()
    }

    private lateinit var mEVRemoteDataSource: EstacionVitalRemoteDataSource
    private lateinit var mDocumentHistoryView: IDocumentHistoryFragmentView
    private lateinit var mSharedPrefManager: SharedPrefManager

    constructor(sharedPrefManager: SharedPrefManager , dataSource: EstacionVitalRemoteDataSource, view: IDocumentHistoryFragmentView) {
        this.mEVRemoteDataSource = dataSource
        this.mDocumentHistoryView = view
        this.mSharedPrefManager = sharedPrefManager
    }
    override fun loadDocuments() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mDocumentHistoryView.showDocumentFetchProgress()
        mEVRemoteDataSource.getDocuments(token, object: IGetDocumentsCallback{
            override fun onTokenExpired() {
                mDocumentHistoryView.hideDocumentFetchProgress()

            }

            override fun onSuccess(response: DocumentsResponse) {
                mDocumentHistoryView.hideDocumentFetchProgress()
                if (response.status == "success") {
                    val paidDocuments = response.documents.filter { it.serviceType == CHAT_PREMIUM }
                    mDocumentHistoryView.updateDocumentsListUI(paidDocuments.toMutableList())
                } else {
                    mDocumentHistoryView.showError()
                }
            }

            override fun onFailure() {
                mDocumentHistoryView.hideDocumentFetchProgress()
            }

        })
    }
}