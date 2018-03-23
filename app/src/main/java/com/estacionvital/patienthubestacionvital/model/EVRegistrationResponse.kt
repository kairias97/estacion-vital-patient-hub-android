package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 28/2/2018.
 */
data class EVRegistrationResponse(@SerializedName("status") val status: String,
                                  @SerializedName("data") val data: List<LoginResponseData>)