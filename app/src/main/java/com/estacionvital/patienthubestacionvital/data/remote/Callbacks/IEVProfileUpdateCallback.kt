package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.EVProfileUpdateResponse

/**
 * Created by dusti on 05/03/2018.
 */
interface IEVProfileUpdateCallback {
    fun onSuccess(result: EVProfileUpdateResponse)
    fun onFailure()
}