package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.NumberVerificationResponse

/**
 * Created by kevin on 25/2/2018.
 */
public interface AuthRegistrationCallback{
    fun onSuccess(response: NumberVerificationResponse)
    fun onFailure()
}