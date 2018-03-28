package com.estacionvital.patienthub.ui.fragmentViews

import com.estacionvital.patienthub.model.EVUserProfile
import com.estacionvital.patienthub.ui.views.IBaseView

/**
 * Created by dusti on 03/03/2018.
 */
interface IProfileFragmentView: IBaseView {
    fun showLoadingProgress()
    fun showErrorLoading()
    fun navToEditProfile()
    fun setProfileData(data: EVUserProfile)
    fun hideLoadingProgress()
}