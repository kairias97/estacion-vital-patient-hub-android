package com.estacionvital.patienthubestacionvital.presenter

import com.twilio.chat.Channel

/**
 * Created by dusti on 20/03/2018.
 */
interface ITwilioChatPresenter {
    fun retrieveChannel(roomID: String)
    fun retrieveMessages(channel: Channel)
    fun setChannelListener(channel: Channel)
    fun sendMessage(channel: Channel, body: String)
}