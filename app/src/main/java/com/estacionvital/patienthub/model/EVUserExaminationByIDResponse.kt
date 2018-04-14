package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 11/4/2018.
 */
data class EVUserExaminationByIDResponse(@SerializedName("status") val status: String,
                                    @SerializedName("data") val data: EVUserNotificationExamination)
