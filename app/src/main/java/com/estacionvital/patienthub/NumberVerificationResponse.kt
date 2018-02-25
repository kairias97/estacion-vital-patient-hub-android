package com.estacionvital.patienthub

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 24/02/2018.
 */
data class NumberVerificationResponse(@SerializedName("status") val status: Int, @SerializedName("msg") val msg: String)