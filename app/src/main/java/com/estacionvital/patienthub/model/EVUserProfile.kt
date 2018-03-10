package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 03/03/2018.
 */
data class EVUserProfile(@SerializedName("email") var email: String, @SerializedName("name") var name: String, @SerializedName("last_name") var last_name: String)