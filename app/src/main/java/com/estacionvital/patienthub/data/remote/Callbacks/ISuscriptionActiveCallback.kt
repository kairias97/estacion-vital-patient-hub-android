package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.SuscriptionActiveResponse

/**
 * Created by dusti on 26/02/2018.
 */
interface ISuscriptionActiveCallback {
    fun onSuccess(response: List<SuscriptionActiveResponse>)
    fun onFailure()
}