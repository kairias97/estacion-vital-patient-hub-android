package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 04/03/2018.
 */
data class EVProfileUpdateRequest(@SerializedName("name") val name: String, @SerializedName("last_name") val last_name: String,
                                  @SerializedName("email") val email: String)