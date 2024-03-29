package com.estacionvital.patienthub.services

/**
 * Created by kevin on 23/3/2018.
 */
import android.app.IntentService
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.model.EVChatSession
import com.estacionvital.patienthub.util.REGISTRATION_COMPLETE

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.twilio.chat.ErrorInfo
import com.twilio.chat.StatusListener

import java.io.IOException

/**
 * Registration intent handles receiving and updating the FCM token lifecycle events.
 */
//IntentService para registro de token de firebase para twilio
class RegistrationIntentService : IntentService("RegistrationIntentService") {
    init {
        Log.i("start","Stared")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.i("onhandleintent","onHandleIntent")
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        try {
            val token = FirebaseInstanceId.getInstance().token
            Log.i("Registration token","FCM Registration Token: " + token!!)

            /**
             * Persist registration to Twilio servers.
             */
            EVChatSession.instance.chatClient?.registerFCMToken(token, object: StatusListener() {
                override fun onSuccess() {
                    Log.i("success fcm", "Token registered succesfully")
                }

                override fun onError(errorInfo: ErrorInfo?) {
                    super.onError(errorInfo)
                }


            })
            //Suscripcion a firebase topics
            subscribeTopics(token)

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean(SharedPrefManager.PreferenceKeys.SENT_TOKEN_TO_SERVER.toString(),
                    true).apply()
        } catch (e: Exception) {
            Log.e("Error","Failed to complete token refresh", e)
            // If an exception happens while fetching the new token or updating our registration
            // data, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(SharedPrefManager.PreferenceKeys.SENT_TOKEN_TO_SERVER.toString(),
                    false).apply()
        }

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        val registrationComplete = Intent(REGISTRATION_COMPLETE)
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete)
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    @Throws(IOException::class)
    private fun subscribeTopics(token: String?) {
         for (topic in TOPICS) {
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/"+topic)
        }
    }

    companion object {


        private val TOPICS = arrayOf("global")
    }
}