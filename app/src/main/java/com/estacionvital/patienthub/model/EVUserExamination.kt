package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 16/03/2018.
 */
data class EVUserExamination(@SerializedName("twilio_channel_name") val channel_name: String, @SerializedName("finished") val finished: Boolean,
                             @SerializedName("examination_type") val examination_type: String, @SerializedName("service_type") val service_type: String)