package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 27/2/2018.
 */
data class ClubSubscriptionResponse(@SerializedName("status") val status: Int,
                                    @SerializedName("msg") val msg: String)