package com.estacionvital.patienthubestacionvital.presenter.implementations

import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IGetDocumentsCallback
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.DocumentsResponse
import com.estacionvital.patienthubestacionvital.model.EVUserSession
import com.estacionvital.patienthubestacionvital.presenter.IDocumentHistoryPresenter
import com.estacionvital.patienthubestacionvital.ui.fragmentViews.IDocumentHistoryFragmentView

/**
 * Created by kevin on 24/3/2018.
 */
class DocumentHistoryPresenterImpl: IDocumentHistoryPresenter {

    private lateinit var mEVRemoteDataSource: EstacionVitalRemoteDataSource
    private lateinit var mDocumentHistoryView: IDocumentHistoryFragmentView

    constructor(dataSource: EstacionVitalRemoteDataSource, view: IDocumentHistoryFragmentView) {
        this.mEVRemoteDataSource = dataSource
        this.mDocumentHistoryView = view
    }
    override fun loadDocuments() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mDocumentHistoryView.showDocumentFetchProgress()
        mEVRemoteDataSource.getDocuments(token, object: IGetDocumentsCallback{
            override fun onSuccess(response: DocumentsResponse) {
                mDocumentHistoryView.hideDocumentFetchProgress()
                if (response.status == "success") {
                    mDocumentHistoryView.updateDocumentsListUI(response.documents.toMutableList())
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