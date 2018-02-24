package com.estacionvital.patienthub.ui.views

/**
 * Created by dusti on 23/02/2018.
 */
interface INumberVerificationView {
    //The presenter will invoke this method from its view's reference
    fun showNumberRequiredMessage(msg: String)
    fun showValidationMessage(msg: String)
    fun showErrorMessage()
    fun showMovistarValidationProgress()
    fun showUserVerificationProgress()
    fun navigateToSMSCodeVerification(isRegisteredUser: Boolean)

}