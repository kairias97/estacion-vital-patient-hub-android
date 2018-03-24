package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 26/02/2018.
 */
data class SuscriptionActiveRequest(@SerializedName("number") val number:String, @SerializedName("auth_credential") val auth_credential: String)