package com.estacionvital.patienthub.ui.fragmentViews

import com.estacionvital.patienthub.model.EVUserProfile

/**
 * Created by dusti on 03/03/2018.
 */
interface IProfileFragmentView {
    fun showLoadingProgress()
    fun showErrorLoading()
    fun navToEditProfile()
    fun setProfileData(data: EVUserProfile)
    fun hideLoadingProgress()
}