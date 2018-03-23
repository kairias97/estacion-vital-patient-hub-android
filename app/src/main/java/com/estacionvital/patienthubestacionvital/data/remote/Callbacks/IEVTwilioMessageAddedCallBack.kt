package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.twilio.chat.Message

/**
 * Created by dusti on 21/03/2018.
 */
interface IEVTwilioMessageAddedCallBack {
    fun onSuccess(message: Message)
    fun onFailure()
}