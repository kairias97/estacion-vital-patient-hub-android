package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.LoginResponse

/**
 * Created by kevin on 25/2/2018.
 */
interface IValidateEVCredentialsCallback {
    fun onSuccess(response: LoginResponse)
    fun onFailure()
}