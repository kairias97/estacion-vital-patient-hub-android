package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVRetrieveDoctorsAvailabilityResponse

/**
 * Created by dusti on 19/03/2018.
 */
interface IEVRetrieveDoctorsAvailabilityCallBack {
    fun onSuccess(response: EVRetrieveDoctorsAvailabilityResponse)
    fun onFailure()
    fun onTokenExpired()
}