package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 22/03/2018.
 */
data class EVValidateCouponResponse(@SerializedName("coupon") val coupon: String, @SerializedName("valid") val valid: Boolean, @SerializedName("message") val messsage: String)