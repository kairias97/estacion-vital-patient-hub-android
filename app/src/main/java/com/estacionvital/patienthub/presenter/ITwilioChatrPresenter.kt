package com.estacionvital.patienthub.presenter

import com.twilio.chat.Channel

/**
 * Created by dusti on 20/03/2018.
 */
interface ITwilioChatrPresenter {
    fun retrieveChannel(roomID: String)
    fun retrieveMessages(channel: Channel)
}