package com.estacionvital.patienthub.ui.views

import com.twilio.chat.Channel
import com.twilio.chat.Message

/**
 * Created by dusti on 20/03/2018.
 */
interface ITwilioChatView : IBaseView{
    fun showMessageLoading()
    fun hideLoading()
    fun showErrorLoading()
    //fun getChannelFromID(channel: Channel)
    fun setChannelMessagesUI(messages: MutableList<Message>)
    fun addMessageToUI(message: Message)
    fun bindMessageTextInputListener()
    fun unbindMessageTextInputListener()
    fun enableMessageTextInput()
    fun disableMessageTextInput()
    fun enableSendButton()
    fun disableSendButton()
    fun showFreeChatDisabledMessage()
    fun showErrorSendingMessage()
    fun showDoctorLeaved()
    fun showDoctorJoined()
    fun showFreeChatBanner()
    fun hideMessagingControls()
    fun showMessagingControls()
    fun showChannelLoadingMessage()
    fun hideChannelLoadingMessage()
}