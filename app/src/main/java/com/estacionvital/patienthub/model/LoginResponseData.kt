package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 25/2/2018.
 */
data class LoginResponseData(@SerializedName("auth_token") val auth_token: String)