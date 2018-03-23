package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 19/03/2018.
 */
data class EVCreateNewExaminationResponse(@SerializedName("success") val success: Boolean, @SerializedName("token") val token: String,
                                          @SerializedName("identity") val identity: String, @SerializedName("room") val room: String)