package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 04/03/2018.
 */
data class EVProfileUpdateResponse(@SerializedName("status") val status: String, @SerializedName("data") val data: EVUserProfile)