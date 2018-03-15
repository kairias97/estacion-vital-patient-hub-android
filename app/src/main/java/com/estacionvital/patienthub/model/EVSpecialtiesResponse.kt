package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 15/03/2018.
 */
data class EVSpecialtiesResponse(@SerializedName("specialties") val specialtes: List<String>)