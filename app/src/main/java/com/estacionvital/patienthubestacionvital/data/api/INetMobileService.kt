package com.estacionvital.patienthubestacionvital.data.api

/**
 * Created by dusti on 24/02/2018.
 */

import com.estacionvital.patienthubestacionvital.model.*
import com.estacionvital.patienthubestacionvital.util.*
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
    @POST(URL_RETRIEVE_SUBSCRIPTION_CATALOG)
    fun retrieveSuscriptionCatalog(@Body body: SuscriptionCatalogRequest):Call<List<EVClub>>
    @POST(URL_RETRIEVE_SUBSCRIPTION_LIMIT)
    fun retrieveSuscriptionLimit(@Body body: SuscriptionLimitRequest): Call<SuscriptionLimitResponse>
    @POST(URL_RETRIEVE_SUBSCRIPTION_ACTIVE)
    fun retrieveSuscriptionActive(@Body body: SuscriptionActiveRequest): Call<List<SuscriptionActiveResponse>>
    @POST(URL_NEW_CLUB_SUBSCRIPTION)
    fun subscribeToEVClub(@Body body: ClubSubscriptionRequest): Call<ClubSubscriptionResponse>

}