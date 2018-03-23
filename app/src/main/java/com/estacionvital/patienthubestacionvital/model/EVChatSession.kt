package com.estacionvital.patienthubestacionvital.model

import com.twilio.chat.ChatClient

/**
 * Created by dusti on 16/03/2018.
 */
class EVChatSession {
    lateinit var chatClient: ChatClient
    var isChatClientCreated: Boolean = false

    companion object {
        val instance: EVChatSession by lazy { EVChatSession() }
    }
}