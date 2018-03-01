package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.model.EVRegistrationRequest
import com.estacionvital.patienthub.model.EVRegistrationResponse
import com.estacionvital.patienthub.model.LoginRequest
import com.estacionvital.patienthub.model.LoginResponse
import com.estacionvital.patienthub.util.URL_EV_LOGIN
import com.estacionvital.patienthub.util.URL_EV_NEW_REGISTRATION
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by kevin on 25/2/2018.
 */
interface IEstacionVitalService{
    @POST(URL_EV_LOGIN)
    fun validateEVCredentials(@Body body: LoginRequest): Call<LoginResponse>
    @POST(URL_EV_NEW_REGISTRATION)
    fun submitRegistrationData(@Header("Authorization") basicAuth: String,
                               @Body body: EVRegistrationRequest): Call<EVRegistrationResponse>
}