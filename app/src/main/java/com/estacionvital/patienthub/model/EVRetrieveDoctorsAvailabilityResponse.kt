package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 19/03/2018.
 */
data class EVRetrieveDoctorsAvailabilityResponse(@SerializedName("availability") val available: Boolean)