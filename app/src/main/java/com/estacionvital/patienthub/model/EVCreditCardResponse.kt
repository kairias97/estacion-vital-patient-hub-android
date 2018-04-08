package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 07/04/2018.
 */
data class EVCreditCardResponse(@SerializedName("status") val status: String, @SerializedName("message") val message: String,
                                @SerializedName("order_id") val order_id: String)