package com.estacionvital.patienthub.model

import com.twilio.chat.Channel

/**
 * Created by dusti on 17/03/2018.
 */
class EVChannel {
    var unique_name: String = ""
    var status: Boolean = true
    var type: String = ""
    var specialty: String = ""
    var twilioChannel: Channel ?= null
}