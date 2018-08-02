package com.estacionvital.patienthub.util

import java.util.regex.Pattern

/**
 * Created by kevin on 28/2/2018.
 */
class RegexUtil {
    //Validar regex de email
    fun isEmailValid(email: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    //Funcion que remplaza todos los numeros en un string con ""
    fun trimNumbersFromString(content: String): String{
        return content.replace("[0-9]".toRegex(), "")
    }
    //Verificar si un string contiene uno o mas digitos
    fun containsDigits(content: String): Boolean{
        val pattern = Pattern.compile("^\\D*[0-9]+\\D*\$")
        return pattern.matcher(content).matches()

    }
    private constructor()
    companion object {
        val instance: RegexUtil by lazy { RegexUtil() }
    }
}