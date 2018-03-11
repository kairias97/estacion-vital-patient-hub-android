package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.LogoutResponse

/**
 * Created by kevin on 10/3/2018.
 */
interface ILogoutCallback {
    fun onSuccess(response: LogoutResponse)
    fun onFailure()
}