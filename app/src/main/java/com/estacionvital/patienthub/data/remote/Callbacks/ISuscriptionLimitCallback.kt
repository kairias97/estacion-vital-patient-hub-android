package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.SuscriptionLimitResponse

/**
 * Created by dusti on 26/02/2018.
 */
interface ISuscriptionLimitCallback {
    fun onSuccess(response: SuscriptionLimitResponse)
    fun onFailure()
}