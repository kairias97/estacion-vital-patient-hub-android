package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 25/4/2018.
 */
data class SuscriptionTotalResponse(@SerializedName("status") val status: Int, @SerializedName("msg") val msg: String, @SerializedName("Total") val total: Int)