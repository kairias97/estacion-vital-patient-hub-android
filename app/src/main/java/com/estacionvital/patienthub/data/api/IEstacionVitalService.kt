package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.util.URL_EV_LOGIN
import com.estacionvital.patienthub.util.URL_EV_NEW_REGISTRATION
import com.estacionvital.patienthub.util.URL_EV_RETRIEVE_PROFILE
import com.estacionvital.patienthub.util.URL_EV_UPDATE_PROFILE
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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
    @GET(URL_EV_RETRIEVE_PROFILE)
    fun retrieveProfileData(@Header("Authorization") basicAuth: String): Call<EVRetrieveProfileResponse>
    @POST(URL_EV_UPDATE_PROFILE)
    fun updateProfile(@Header("Authorization") basicAuth: String,
                      @Body body: EVProfileUpdateRequest): Call<EVProfileUpdateResponse>
}