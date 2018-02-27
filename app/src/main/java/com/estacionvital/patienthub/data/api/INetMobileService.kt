package com.estacionvital.patienthub.data.api

/**
 * Created by dusti on 24/02/2018.
 */

import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.util.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface INetMobileService {
    @POST(URL_VERIFY_NUMBER)
    fun verifyNumber(@Body body: NumberVerificationRequest): Call<NumberVerificationResponse>
    @POST(URL_SEND_SMS)
    fun sendSMSCode(@Body body: SendSMSRequest): Call<SendSMSResponse>
    @POST(URL_VALIDATE_PIN)
    fun validatePin(@Body body: ValidatePinRequest): Call<ValidatePinResponse>
    @POST(URL_RETRIEVE_SUSCRIPTION_CATALOG)
    fun retrieveSuscriptionCatalog(@Body body: SuscriptionCatalogRequest):Call<List<EVClub>>
    @POST(URL_RETRIEVE_SUSCRIPTION_LIMIT)
    fun retrieveSuscriptionLimit(@Body body: SuscriptionLimitRequest): Call<SuscriptionLimitResponse>
    @POST(URL_RETRIVE_SUSCRIPTION_ACTIVE)
    fun retrieveSuscriptionActive(@Body body: SuscriptionActiveRequest): Call<List<SuscriptionActiveResponse>>
}