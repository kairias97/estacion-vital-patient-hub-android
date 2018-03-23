package com.estacionvital.patienthubestacionvital.services

/**
 * Created by kevin on 23/3/2018.
 */
import android.content.Intent
import android.util.Log

import com.google.firebase.iid.FirebaseInstanceIdService

class FCMInstanceIDService : FirebaseInstanceIdService() {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    override fun onTokenRefresh() {
        Log.e("tokenRefresh","onTokenRefresh")

        // Fetch updated Instance ID token and notify our app's server of any changes.
        val intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent)
    }

}