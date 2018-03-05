package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by dusti on 04/03/2018.
 */
data class EVProfileUpdateRequest(@SerializedName("name") val name: String, @SerializedName("last_name") val last_name: String,
                                  @SerializedName("email") val email: String, @SerializedName("birth_date") val date: Date,
                                  @SerializedName("phone_number") val phone_number: String, @SerializedName("is_male") val is_male: String)