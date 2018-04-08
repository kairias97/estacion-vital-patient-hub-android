package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 07/04/2018.
 */
data class EVPaymentInfo(@SerializedName("type") val type: String, @SerializedName("code") val code: String,
                         @SerializedName("order_id") val order_id: String)