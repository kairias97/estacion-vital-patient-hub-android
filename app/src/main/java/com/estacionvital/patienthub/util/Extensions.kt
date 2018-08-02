package com.estacionvital.patienthub.util

import android.content.Context
import android.util.Base64
import android.util.Base64.DEFAULT
import android.widget.Toast

/**
 * Created by kevin on 25/2/2018.
 */
//Extension kotlin para convertir cualquier string a base 64
fun String.toBase64(): String {
    return Base64.encodeToString(this.toByteArray(), DEFAULT)
}
//Extension kotlin para poder lanzar un toast en cualquier context de Android usando string
fun Context.toast(msg: String, length: Int = Toast.LENGTH_LONG){
    Toast.makeText(this, msg, length).show()
}

//Extension kotlin para poder lanzar un toast en cualquier context de Android usando resourceID
fun Context.toast(resID: Int, length: Int = Toast.LENGTH_LONG){
    Toast.makeText(this, resID, length).show()
}