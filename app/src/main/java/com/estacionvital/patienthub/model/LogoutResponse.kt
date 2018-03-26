package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 10/3/2018.
 */
data class LogoutResponse(@SerializedName("status") val status: String)