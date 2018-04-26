package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.SuscriptionTotalResponse

/**
 * Created by kevin on 25/4/2018.
 */
interface ISuscriptionTotalCallback {
    fun onSuccess(response: SuscriptionTotalResponse)
    fun onFailure()
}