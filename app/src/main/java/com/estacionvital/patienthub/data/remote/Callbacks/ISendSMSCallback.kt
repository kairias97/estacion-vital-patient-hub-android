package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.NumberVerificationResponse
import com.estacionvital.patienthub.model.SendSMSResponse

/**
 * Created by kevin on 25/2/2018.
 */
interface ISendSMSCallback {
    fun onSuccess(response: SendSMSResponse)
    fun onFailure()
}