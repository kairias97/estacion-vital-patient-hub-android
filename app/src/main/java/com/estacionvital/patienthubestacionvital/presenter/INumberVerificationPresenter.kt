package com.estacionvital.patienthubestacionvital.presenter

/**
 * Created by kevin on 24/2/2018.
 */
interface INumberVerificationPresenter {
    fun validateNumber(phoneNumber: String)
    fun checkNumberChanged(phoneNumber: String)
}