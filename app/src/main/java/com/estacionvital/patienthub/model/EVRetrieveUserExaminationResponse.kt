package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 16/03/2018.
 */
data class EVRetrieveUserExaminationResponse(@SerializedName("status") val status: String, @SerializedName("data") val data: EVUserExaminationData)