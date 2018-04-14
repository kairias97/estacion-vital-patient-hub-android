package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVUserExaminationByIDResponse

/**
 * Created by kevin on 11/4/2018.
 */
interface IGetChannelByUniqueName {
    fun onSuccess(response: EVUserExaminationByIDResponse)
    fun onFailure()
    fun onTokenExpired()
}