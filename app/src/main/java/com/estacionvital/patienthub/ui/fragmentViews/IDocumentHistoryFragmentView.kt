package com.estacionvital.patienthub.ui.fragmentViews

import com.estacionvital.patienthub.model.Document
import com.estacionvital.patienthub.ui.views.IBaseView

/**
 * Created by kevin on 24/3/2018.
 */
interface IDocumentHistoryFragmentView: IBaseView {
    fun showDocumentFetchProgress()
    fun hideDocumentFetchProgress()
    fun updateDocumentsListUI(documents: MutableList<Document>)
    fun showError()

}