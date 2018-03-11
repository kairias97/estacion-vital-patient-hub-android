package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.EVRetrieveProfileResponse
import com.estacionvital.patienthub.model.EVUserProfile

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