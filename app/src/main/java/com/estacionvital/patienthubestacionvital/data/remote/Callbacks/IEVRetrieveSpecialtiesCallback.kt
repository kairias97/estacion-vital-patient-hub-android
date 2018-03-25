package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.EVSpecialtiesResponse

/**
 * Created by dusti on 15/03/2018.
 */
interface IEVRetrieveSpecialtiesCallback {
    fun onSuccess(response: EVSpecialtiesResponse)
    fun onFailure()
    fun onTokenExpired()
}