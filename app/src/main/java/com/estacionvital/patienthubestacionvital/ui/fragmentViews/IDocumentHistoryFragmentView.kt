package com.estacionvital.patienthubestacionvital.ui.fragmentViews

import com.estacionvital.patienthubestacionvital.model.Document

/**
 * Created by kevin on 24/3/2018.
 */
interface IDocumentHistoryFragmentView {
    fun showDocumentFetchProgress()
    fun hideDocumentFetchProgress()
    fun updateDocumentsListUI(documents: MutableList<Document>)
    fun showError()

}