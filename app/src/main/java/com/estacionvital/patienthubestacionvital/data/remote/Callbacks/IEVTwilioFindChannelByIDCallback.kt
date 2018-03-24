package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.twilio.chat.Channel

/**
 * Created by dusti on 17/03/2018.
 */
interface IEVTwilioFindChannelByIDCallback {
    fun onSuccess(channel: Channel)
    fun onFailure()
}