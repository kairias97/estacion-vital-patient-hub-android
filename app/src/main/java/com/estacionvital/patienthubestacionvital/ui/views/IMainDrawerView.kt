package com.estacionvital.patienthubestacionvital.ui.views

import com.estacionvital.patienthubestacionvital.model.EVUserProfile

/**
 * Created by dusti on 03/03/2018.
 */
interface IMainDrawerView {

    fun showError()
    fun setDrawerHeaderData(data: EVUserProfile)
    fun navigateToNumberVerification()
    fun showLoggingOutProgress()
    fun hideLoggingOutProgress()
}