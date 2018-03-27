package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 25/2/2018.
 */
class LoginResponse(@SerializedName("isFinished") val status: String,
                    @SerializedName("data") val data: List<LoginResponseData>)
