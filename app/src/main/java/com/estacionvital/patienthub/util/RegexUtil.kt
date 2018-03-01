package com.estacionvital.patienthub.util

/**
 * Created by kevin on 28/2/2018.
 */
class RegexUtil {
    fun isEmailValid(email: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private constructor(){

    }
    companion object {
        val instance: RegexUtil by lazy { RegexUtil() }
    }
}