package com.estacionvital.patienthub.ui.views

/**
 * Created by dusti on 23/02/2018.
 */
interface INumberVerificationView {
    //Input validations
    fun showNumberRequiredMessage()
    fun showNumberInputInvalidMessage()
    fun hidePhoneNumberInputMessage()
    //Webservice result behaviours
    fun showInvalidNumberMessage()
    fun showInternalErrorMessage()
    fun showMovistarValidationProgress()
    fun dismissMovistarValidationProgress()
    fun dismissSMSRequestProgress()
    fun showSMSRequestProgress()
    //Navegability
    fun navigateToSMSCodeVerification(phoneNumber:String)

}