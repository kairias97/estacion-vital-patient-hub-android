package com.estacionvital.patienthub.data.local

import android.content.SharedPreferences

/**
 * Created by kevin on 25/2/2018.
 */
//Clase wrapper para hacer operaciones sobre shared preferences de android
class SharedPrefManager(private val mSharedPref: SharedPreferences) {

    //Obtener string de sharedPref
    fun getSharedPrefString(key: PreferenceKeys): String {
        return mSharedPref.getString(key.toString(), "")
    }
    //Guardar string en sharedPref
    fun saveString(key: PreferenceKeys, value: String) {
        val editor: SharedPreferences.Editor = mSharedPref.edit()
        editor.putString(key.toString(), value)
        editor.commit()

    }
    //Leer bool de shared pref
    fun getSharedPrefBoolean(key: PreferenceKeys): Boolean {
        return mSharedPref.getBoolean(key.toString(), false)
    }
    //Leer float de sharedPref
    fun getSharedPrefFloat(key: PreferenceKeys): Float {
        return mSharedPref.getFloat(key.toString(), 0.toFloat())
    }

    //Enum para los archivos de preferencias usados
    enum class PreferenceFiles {
        UserSharedPref
    }
    //KEYS de valores guardados en shared pref
    enum class PreferenceKeys {
        AUTH_TOKEN,

        PHONE_NUMBER,

        SENT_TOKEN_TO_SERVER
    }
    //Borrar contenido de shared preferences
    fun clearPreferences() {
        mSharedPref.edit().clear().commit()
    }
}