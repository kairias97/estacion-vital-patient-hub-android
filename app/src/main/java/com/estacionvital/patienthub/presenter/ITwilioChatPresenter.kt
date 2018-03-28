package com.estacionvital.patienthub.presenter

import com.estacionvital.patienthub.model.EVChannel
import com.twilio.chat.Channel

/**
 * Created by dusti on 20/03/2018.
 */
interface ITwilioChatPresenter {
    fun setupChatChannel(channel: EVChannel)
    /*fun retrieveChannel(roomID: String)
    fun retrieveMessages(channel: Channel)
    fun setChannelListener(channel: Channel)
    */
    fun sendMessage(channel: EVChannel, body: String)
    fun onMessageTextChanged(msg: String)
}