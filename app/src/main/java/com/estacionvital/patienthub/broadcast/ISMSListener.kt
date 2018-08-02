package com.estacionvital.patienthub.broadcast

/**
 * Created by kevin on 1/3/2018.
 */
//Abstracción para obtener un mensaje sms recibido
interface ISMSListener {
    fun messageReceived(messageText: String)
}