package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.EVRetrieveProfileResponse

/**
 * Created by dusti on 03/03/2018.
 */
interface IEVRetrieveProfileCallback {
    fun onSuccess(result: EVRetrieveProfileResponse)
    fun onFailure()
}