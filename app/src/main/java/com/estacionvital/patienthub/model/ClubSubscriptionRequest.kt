package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 27/2/2018.
 */
data class ClubSubscriptionRequest(@SerializedName("number") val number: String,
                                   @SerializedName("auth_credential") val auth_credential: String,
                                   @SerializedName("service_id") val service_id: Int) {
}