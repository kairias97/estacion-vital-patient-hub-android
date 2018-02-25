package com.estacionvital.patienthub.ui.views

/**
 * Created by dusti on 24/02/2018.
 */
interface IConfirmationCodeVerificationView {
    fun showCodeRequiredMessage(msg: String)
    fun showValidationMessage(msg: String)
    fun showErrorMessage()
    fun navigateToProfileRegistration()
}