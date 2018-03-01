package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVRegistrationResponse

/**
 * Created by kevin on 28/2/2018.
 */
interface IEVRegistrationSubmittedCallback {
    fun onSuccess(response: EVRegistrationResponse)
    fun onFailure()
}