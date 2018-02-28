package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.ClubSubscriptionResponse

/**
 * Created by kevin on 27/2/2018.
 */
interface INewClubSubscriptionCallback {
    fun onSuccess(result: ClubSubscriptionResponse)
    fun onFailure()
}