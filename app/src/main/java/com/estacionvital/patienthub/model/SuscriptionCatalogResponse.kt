package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 26/02/2018.
 */
data class SuscriptionCatalogResponse(@SerializedName("id") val id: String, @SerializedName("name") val name: String, val isRegistered: Boolean)