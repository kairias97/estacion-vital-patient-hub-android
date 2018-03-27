package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 24/3/2018.
 */
data class DocumentsResponse(@SerializedName("isFinished") val status: String,
                             @SerializedName("data") val documents: List<Document>)