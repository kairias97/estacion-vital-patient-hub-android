package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 25/4/2018.
 */
data class SuscriptionTotalRequest(@SerializedName("number") val number:String, @SerializedName("auth_credential") val auth_credential: String)