package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.SendSMSResponse

/**
 * Created by kevin on 25/2/2018.
 */
interface ISendSMSCallback {
    fun onSuccess(response: SendSMSResponse)
    fun onFailure()
}