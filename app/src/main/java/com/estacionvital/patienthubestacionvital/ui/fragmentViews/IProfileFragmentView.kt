package com.estacionvital.patienthubestacionvital.ui.fragmentViews

import com.estacionvital.patienthubestacionvital.model.EVUserProfile
import com.estacionvital.patienthubestacionvital.ui.views.IBaseView

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