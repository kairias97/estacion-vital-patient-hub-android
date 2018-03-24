package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 16/03/2018.
 */
data class EVRetrieveUserExaminationRequest(@SerializedName("service") val service: String)