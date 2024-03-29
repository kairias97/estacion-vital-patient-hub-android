package com.estacionvital.patienthub.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage

/**
 * Created by kevin on 1/3/2018.
 */
//Broadcast receiver que permite detectar si el telefono recibe un mensaje y obtener su contenido
class SMSReceiver: BroadcastReceiver() {


    override fun onReceive(c: Context?, intent: Intent?) {
        val bundle = intent!!.extras
        if (bundle != null) {
            var pduObjects = bundle.get("pdus") as Array<Object>
            for (i in 0.. pduObjects.size - 1) {
                val dataByteArray = pduObjects[i] as ByteArray

                val smsMessage = SmsMessage.createFromPdu(dataByteArray)
                val sender = smsMessage.displayOriginatingAddress
                val messageBody = smsMessage.messageBody
                //Se usa esta interfaz para mandar el contenido del sms
                mListener.messageReceived(messageBody)



            }
        }

    }
    companion object {
        lateinit var mListener: ISMSListener
        fun bindListener(listener: ISMSListener){
            this.mListener = listener
        }
    }



}