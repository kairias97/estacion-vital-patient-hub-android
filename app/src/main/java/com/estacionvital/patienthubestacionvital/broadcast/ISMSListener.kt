package com.estacionvital.patienthubestacionvital.broadcast

/**
 * Created by kevin on 1/3/2018.
 */
interface ISMSListener {
    fun messageReceived(messageText: String)
}