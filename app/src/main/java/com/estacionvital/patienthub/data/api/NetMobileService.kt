package com.estacionvital.patienthub.data.api

/**
 * Created by dusti on 24/02/2018.
 */

import com.estacionvital.patienthub.NumberVerification
import com.estacionvital.patienthub.NumberVerificationResponse
import com.estacionvital.patienthub.util.URL_VERIFY_NUMBER
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetMobileService {
    @POST(URL_VERIFY_NUMBER)
    fun verifyNumber(@Body body: NumberVerification): Call<NumberVerificationResponse>

}