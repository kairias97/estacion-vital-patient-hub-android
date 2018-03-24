package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.EVClub

/**
 * Created by dusti on 26/02/2018.
 */
interface ISuscriptionCatalogCallback {
    fun onSuccess(response: List<EVClub>)
    fun onFailure()
}