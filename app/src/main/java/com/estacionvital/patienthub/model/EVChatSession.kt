package com.estacionvital.patienthub.model

import com.twilio.chat.ChatClient

/**
 * Created by dusti on 16/03/2018.
 */
class EVChatSession {
    var chatClient: ChatClient? = null
    var isChatClientCreated: Boolean = false

    companion object {
        val instance: EVChatSession by lazy { EVChatSession() }
    }
}