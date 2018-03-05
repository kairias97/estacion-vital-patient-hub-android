package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.EVRetrieveProfileResponse
import com.estacionvital.patienthub.model.EVUserProfile

/**
 * Created by dusti on 03/03/2018.
 */
interface IMainDrawerView {
    fun showLoadingProgress()
    fun hideLoadingProgress()
    fun showError()
    fun retrieveData(data: EVUserProfile)
}