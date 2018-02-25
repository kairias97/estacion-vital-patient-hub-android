package com.estacionvital.patienthub.data.api

/**
 * Created by dusti on 24/02/2018.
 */

import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.util.URL_SEND_SMS
import com.estacionvital.patienthub.util.URL_VERIFY_NUMBER
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface INetMobileService {
    @POST(URL_VERIFY_NUMBER)
    fun verifyNumber(@Body body: NumberVerificationRequest): Call<NumberVerificationResponse>
    @POST(URL_SEND_SMS)
    fun sendSMSCode(@Body body: SendSMSRequest): Call<SendSMSResponse>
    @POST(URL_VERIFY_NUMBER)
    fun validatePin(@Body body: ValidatePinRequest): Call<ValidatePinResponse>

}