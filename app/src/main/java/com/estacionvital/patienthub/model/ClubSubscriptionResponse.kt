package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 27/2/2018.
 */
data class ClubSubscriptionResponse(@SerializedName("isFinished") val status: Int,
                                    @SerializedName("msg") val msg: String)