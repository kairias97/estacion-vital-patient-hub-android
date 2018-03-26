package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 22/03/2018.
 */
data class EVValidateCouponRequest(@SerializedName("code") val code: String)