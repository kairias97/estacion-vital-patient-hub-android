package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.ValidatePinResponse

/**
 * Created by kevin on 25/2/2018.
 */
interface IValidatePinCallback {
    fun onSuccess(response: ValidatePinResponse)
    fun onFailure()
}