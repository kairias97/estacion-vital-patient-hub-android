package com.estacionvital.patienthubestacionvital.ui.views

import com.twilio.chat.Channel
import com.twilio.chat.Message

/**
 * Created by dusti on 20/03/2018.
 */
interface ITwilioChatView : IBaseView{
    fun showMessageLoading()
    fun hideLoading()
    fun showErrorLoading()
    fun getChannelFromID(channel: Channel)
    fun getMessagesFromChannel(messages: MutableList<Message>)
    fun getNewMessage(message: Message)
    fun sendMessage(body: String)
}