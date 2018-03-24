package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 19/03/2018.
 */
data class EVCreateNewExaminationRequest(@SerializedName("specialty") val specialty: String,
                                         @SerializedName("examination_type") val examination_type: String,
                                         @SerializedName("service_type") val service_type: String)