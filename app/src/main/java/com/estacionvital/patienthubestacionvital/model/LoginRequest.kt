package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 25/2/2018.
 */
data class LoginRequest(@SerializedName("user_credentials") val user_credentials: String)