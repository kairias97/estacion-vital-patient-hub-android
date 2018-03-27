package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 03/03/2018.
 */
data class EVRetrieveProfileResponse(@SerializedName("isFinished") val status: String, @SerializedName("data") val data: EVUserProfile)