package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 07/04/2018.
 */
data class EVCreditCardRequest(@SerializedName("holder") val holder: String, @SerializedName("number") val number: String,
                               @SerializedName("expMonth") val expMonth: String, @SerializedName("expYear") val expYear: String,
                               @SerializedName("cvc") val cvc: String)