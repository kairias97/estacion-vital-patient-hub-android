package com.estacionvital.patienthub.services

/**
 * Created by kevin on 23/3/2018.
 */
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.EVChatSession
import com.estacionvital.patienthub.ui.activities.SplashActivity

import com.google.firebase.messaging.FirebaseMessagingService

import com.google.firebase.messaging.RemoteMessage
import com.twilio.chat.NotificationPayload

import org.json.JSONObject
//Clase para recibir mensajes de notificaciones de firebase
class FCMListenerService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        Log.d("event","onMessageReceived for FCM")

        Log.d("from","From: " + remoteMessage!!.from)

        // Check if message contains a data payload.

        if (remoteMessage.data.size > 0) {
            Log.d("Data message body","Data Message Body: " + remoteMessage.data)

            val obj = JSONObject(remoteMessage.data)
            val data = Bundle()
            //This is the value we would use to notify chat
            val channelID: String = obj.optString("channel_title")

            data.putString("channel_id",obj.optString("channel_id"))
            data.putString("message_id", obj.optString("message_id"))
            data.putString("author", obj.optString("author"))
            data.putString("message_sid", obj.optString("message_sid"))
            data.putString("twi_sound", obj.optString("twi_sound"))
            data.putString("twi_message_type", obj.optString("twi_message_type"))
            data.putString("channel_sid", obj.optString("channel_sid"))
            data.putString("twi_message_id", obj.optString("twi_message_id"))
            data.putString("twi_body", obj.optString("twi_body"))
            data.putString("channel_title",channelID)

            val payload = NotificationPayload(data)
            //Cliente de chat de twilio
            val client = EVChatSession.instance.chatClient
            if (client != null) {
                client!!.handleNotification(payload)
            }

            val type = payload.type

            if (type == NotificationPayload.Type.UNKNOWN) return  // Ignore everything we don't support

            var title = getString(R.string.default_notification_title)

            if (type == NotificationPayload.Type.NEW_MESSAGE)
                title = getString(R.string.title_notification_new_message)
            if (type == NotificationPayload.Type.ADDED_TO_CHANNEL)
                title = getString(R.string.title_notification_added_channel)
            if (type == NotificationPayload.Type.INVITED_TO_CHANNEL)
                title = getString(R.string.title_notification_invited_to_channel)
            if (type == NotificationPayload.Type.REMOVED_FROM_CHANNEL)
                title = getString(R.string.title_notification_removed_from_channel)

            // Set up action Intent
            val intent = Intent(this, SplashActivity::class.java)
            intent.putExtra("notificationChannelID", channelID)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            //val cSid = payload.channelSid

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            //Preparacion para mostrar la notificacion
            val notification = NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(title)
                    .setContentText(payload.body)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setColor(resources.getColor(R.color.colorPrimary))
                    .build()
            /*
            val soundFileName = payload.sound
            if (resources.getIdentifier(soundFileName, "raw", packageName) != 0) {
                val sound = Uri.parse("android.resource://$packageName/raw/$soundFileName")
                notification.defaults = notification.defaults and Notification.DEFAULT_SOUND.inv()
                notification.sound = sound
                Log.d("Test","Playing specified sound $soundFileName")
            } else {
                notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
                Log.d("sound","Playing default sound")
            }
            */

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(0, notification)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d("","Notification Message Body: " + remoteMessage.notification!!.body!!)
            Log.e("Error", "We do not parse notification body - leave it to system")
        }
    }

}