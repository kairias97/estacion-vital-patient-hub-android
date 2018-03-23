package com.estacionvital.patienthubestacionvital.util

import java.util.regex.Pattern

/**
 * Created by kevin on 28/2/2018.
 */
class RegexUtil {
    fun isEmailValid(email: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun trimNumbersFromString(content: String): String{
        return content.replace("[0-9]".toRegex(), "")
    }
    fun containsDigits(content: String): Boolean{
        val pattern = Pattern.compile("^\\D*[0-9]+\\D*\$")
        return pattern.matcher(content).matches()

    }
    private constructor()
    companion object {
        val instance: RegexUtil by lazy { RegexUtil() }
    }
}