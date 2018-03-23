package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.twilio.chat.Message

/**
 * Created by dusti on 20/03/2018.
 */
interface IEVTwilioGetLastMessagesFromChannelCalBack {
    fun onSuccess(messages: List<Message>)
    fun onFailure()
}