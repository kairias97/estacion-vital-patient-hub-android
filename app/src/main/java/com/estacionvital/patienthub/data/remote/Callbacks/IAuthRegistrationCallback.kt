package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.NumberVerificationResponse

/**
 * Created by kevin on 25/2/2018.
 */
public interface IAuthRegistrationCallback: IBaseCallback{
    fun onSuccess(response: NumberVerificationResponse)

    fun onFailure()
}