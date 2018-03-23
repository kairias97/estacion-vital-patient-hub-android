package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 28/2/2018.
 */
data class EVRegistrationRequest(@SerializedName("name") val name:String,
                                 @SerializedName("last_name") val last_name: String,
                                 @SerializedName("email") val email: String,
                                 @SerializedName("birth_date")val birth_date: String,
                                 @SerializedName("phone_number")val phone_number: String,
                                 @SerializedName("is_male") val is_male: String)