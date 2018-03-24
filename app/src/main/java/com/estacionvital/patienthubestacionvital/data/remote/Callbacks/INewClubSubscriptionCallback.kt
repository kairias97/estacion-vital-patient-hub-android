package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.ClubSubscriptionResponse

/**
 * Created by kevin on 27/2/2018.
 */
interface INewClubSubscriptionCallback {
    fun onSuccess(result: ClubSubscriptionResponse)
    fun onFailure()
}