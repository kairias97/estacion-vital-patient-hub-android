package com.estacionvital.patienthub.data.local

import android.app.PendingIntent.getActivity
import android.content.SharedPreferences
import android.preference.Preference

/**
 * Created by kevin on 25/2/2018.
 */
class SharedPrefManager(private val mSharedPref: SharedPreferences) {


    fun getSharedPrefString(key: PreferenceKeys): String {
        return mSharedPref.getString(key.toString(), "")
    }

    fun getSharedPrefBoolean(key: PreferenceKeys): Boolean {
        return mSharedPref.getBoolean(key.toString(), false)
    }
    fun getSharedPrefFloat(key: PreferenceKeys): Float {
        return mSharedPref.getFloat(key.toString(), 0.toFloat())
    }


    enum class PreferenceFiles {
        UserSharedPref
    }
    enum class PreferenceKeys {
        AuthToken
    }
}