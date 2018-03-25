package com.estacionvital.patienthubestacionvital.ui.fragmentViews

import com.estacionvital.patienthubestacionvital.model.EVChannel
import com.estacionvital.patienthubestacionvital.model.EVUserExaminationData
import com.estacionvital.patienthubestacionvital.ui.views.IBaseView

/**
 * Created by dusti on 14/03/2018.
 */
interface IConversationHistoryFragmentView: IBaseView{
    fun navigateToSpecialty()
    fun showLoadingProgress()
    fun hideLoading()
    fun showError()
    fun setHistory(data: EVUserExaminationData)
    fun setChannelList(data: MutableList<EVChannel>)
}