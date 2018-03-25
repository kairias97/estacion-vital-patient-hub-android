package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 24/3/2018.
 */
data class DocumentsResponse(@SerializedName("status") val status: String,
                             @SerializedName("data") val documents: List<Document>)