package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.SuscriptionActiveResponse

/**
 * Created by dusti on 26/02/2018.
 */
interface ISuscriptionActiveCallback {
    fun onSuccess(response: List<SuscriptionActiveResponse>)
    fun onFailure()
}