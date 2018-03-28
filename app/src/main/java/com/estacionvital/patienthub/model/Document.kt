package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 24/3/2018.
 */
data class Document(@SerializedName("specialty") val specialty: String,
                    @SerializedName("examination_type") val examinationType: String,
                    @SerializedName("service_type") val serviceType: String,
                    @SerializedName("url") val url: String,
                    @SerializedName("created_at") val createdAt: String)