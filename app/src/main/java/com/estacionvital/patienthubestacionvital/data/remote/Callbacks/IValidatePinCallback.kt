package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.ValidatePinResponse

/**
 * Created by kevin on 25/2/2018.
 */
interface IValidatePinCallback {
    fun onSuccess(response: ValidatePinResponse)
    fun onFailure()
}