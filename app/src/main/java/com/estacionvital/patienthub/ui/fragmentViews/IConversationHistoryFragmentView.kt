package com.estacionvital.patienthub.ui.fragmentViews

import com.estacionvital.patienthub.model.EVRetrieveUserExaminationResponse

/**
 * Created by dusti on 14/03/2018.
 */
interface IConversationHistoryFragmentView {
    fun navigateToSpecialty()
    fun showLoadingProgress()
    fun hideLoading()
    fun showError()
    fun setHistory(data: EVRetrieveUserExaminationResponse)
}