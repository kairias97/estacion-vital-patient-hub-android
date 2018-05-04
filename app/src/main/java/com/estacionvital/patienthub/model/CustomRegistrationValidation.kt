package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 3/5/2018.
 */
data class CustomRegistrationValidation(@SerializedName("status") val status: String,
                                        @SerializedName("message") val message: String)