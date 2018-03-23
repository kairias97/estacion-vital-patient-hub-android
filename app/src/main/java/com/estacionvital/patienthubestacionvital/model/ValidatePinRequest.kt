package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 25/2/2018.
 */
data class ValidatePinRequest(@SerializedName("number") val number: String, @SerializedName("pin") val pin: String, @SerializedName("auth_credential") val auth_credential: String)