package com.estacionvital.patienthub.ui.views

/**
 * Created by kevin on 22/2/2018.
 */
interface IBaseView {
    fun showProgressDialog(msg:String): Unit {}
    fun hideProgressDialog(): Unit{}
    fun showExpirationMessage(): Unit
}