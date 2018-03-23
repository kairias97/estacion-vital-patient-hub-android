package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 16/03/2018.
 */
data class EVUserExaminationData(@SerializedName("examinations") val examinations: List<EVUserExamination>, @SerializedName("twilio_token") val twilio_token: String,
                                 @SerializedName("created_at") val created_at: String)