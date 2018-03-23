package com.estacionvital.patienthubestacionvital.presenter

/**
 * Created by kevin on 25/2/2018.
 */
interface IConfirmationCodePresenter {
    fun validateCode(confirmationCode: String)
    fun checkCodeChanged(confirmationCode: String)
}