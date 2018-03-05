package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 03/03/2018.
 */
data class EVUserProfile(@SerializedName("email") val email: String, @SerializedName("name") val name: String, @SerializedName("last_name") val last_name: String)