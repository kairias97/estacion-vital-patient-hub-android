package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVSpecialtiesResponse

/**
 * Created by dusti on 15/03/2018.
 */
interface IEVRetrieveSpecialtiesCallback {
    fun onSuccess(response: EVSpecialtiesResponse)
    fun onFailure()
    fun onTokenExpired()
}