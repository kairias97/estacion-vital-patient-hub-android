package com.estacionvital.patienthub

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 24/02/2018.
 */
data class NumberVerification(@SerializedName("number") val number: String, @SerializedName("auth_credential") val auth_credential: String)