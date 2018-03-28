package com.estacionvital.patienthub.ui.fragmentViews

import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.model.EVUserExaminationData
import com.estacionvital.patienthub.ui.views.IBaseView

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