package com.estacionvital.patienthub.broadcast

/**
 * Created by kevin on 1/3/2018.
 */
interface ISMSListener {
    fun messageReceived(messageText: String)
}