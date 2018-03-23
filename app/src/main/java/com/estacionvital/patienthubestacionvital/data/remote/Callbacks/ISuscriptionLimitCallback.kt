package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.SuscriptionLimitResponse

/**
 * Created by dusti on 26/02/2018.
 */
interface ISuscriptionLimitCallback {
    fun onSuccess(response: SuscriptionLimitResponse)
    fun onFailure()
}