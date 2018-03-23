package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 25/2/2018.
 */
data class ValidatePinResponse(@SerializedName("status") val status: Int, @SerializedName("msg") val msg: String)