package com.estacionvital.patienthub.presenter

/**
 * Created by kevin on 25/2/2018.
 */
interface IConfirmationCodePresenter {
    fun validateCode(phoneNumber: String, confirmationCode: String)
    fun checkNumberChanged(confirmationCode: String)
}