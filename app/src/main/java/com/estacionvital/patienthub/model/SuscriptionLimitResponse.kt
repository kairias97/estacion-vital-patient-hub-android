package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 26/02/2018.
 */
data class SuscriptionLimitResponse(@SerializedName("isFinished") val status: Int, @SerializedName("msg") val msg: String, @SerializedName("max") val max: Int)