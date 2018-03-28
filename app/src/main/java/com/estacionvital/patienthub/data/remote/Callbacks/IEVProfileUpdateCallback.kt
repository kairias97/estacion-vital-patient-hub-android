package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVProfileUpdateResponse

/**
 * Created by dusti on 05/03/2018.
 */
interface IEVProfileUpdateCallback {
    fun onSuccess(result: EVProfileUpdateResponse)
    fun onFailure()
    fun onTokenExpired()
}