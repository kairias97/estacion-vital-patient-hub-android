package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.model.LoginRequest
import com.estacionvital.patienthub.model.LoginResponse
import com.estacionvital.patienthub.util.EV_LOGIN_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by kevin on 25/2/2018.
 */
interface IEstacionVitalService{
    @POST(EV_LOGIN_URL)
    fun validateEVCredentials(@Body body: LoginRequest): Call<LoginResponse>
}