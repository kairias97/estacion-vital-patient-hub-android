package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.LogoutResponse

/**
 * Created by kevin on 10/3/2018.
 */
interface ILogoutCallback {
    fun onSuccess(response: LogoutResponse)
    fun onFailure()
    fun onTokenExpired()
}