package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.EVRetrieveDoctorsAvailabilityResponse

/**
 * Created by dusti on 19/03/2018.
 */
interface IEVRetrieveDoctorsAvailabilityCallBack {
    fun onSuccess(response: EVRetrieveDoctorsAvailabilityResponse)
    fun onFailure()
}