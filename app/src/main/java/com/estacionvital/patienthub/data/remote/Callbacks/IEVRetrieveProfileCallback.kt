package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVRetrieveProfileResponse

/**
 * Created by dusti on 03/03/2018.
 */
interface IEVRetrieveProfileCallback {
    fun onSuccess(result: EVRetrieveProfileResponse)
    fun onFailure()

    fun onTokenExpired()
}