package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.util.*
import retrofit2.Call
import retrofit2.http.*

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
    @DELETE(URL_EV_LOGOUT)
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>
    @GET(URL_EV_RETRIEVE_SPECIALTIES)
    fun retrieveSpecialties(@Header("Authorization") token: String): Call<EVSpecialtiesResponse>
    @POST(URL_EV_RETRIEVE_EXAMINATIONS)
    fun retrieveExaminations(@Header("Authorization") token: String, @Body body: EVRetrieveUserExaminationRequest): Call<EVRetrieveUserExaminationResponse>

}