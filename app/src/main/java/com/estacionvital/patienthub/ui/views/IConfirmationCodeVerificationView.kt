package com.estacionvital.patienthub.ui.views

/**
 * Created by dusti on 24/02/2018.
 */
interface IConfirmationCodeVerificationView : IBaseView{
    //Input validations
    fun showCodeRequiredMessage()
    fun hideCodeInputMessage()
    //Webservice result behaviours
    fun showCustomMessage(msg:String)
    fun showInternalErrorMessage()
    fun showCodeValidationProgress()
    fun dismissCodeValidationProgress()
    fun dismissEVLoginRequestProgress()
    fun showEVLoginRequestProgress()
    //Navegability
    fun navigateToMain()
    fun navigateToConfirmSuscription()
    fun navigateToClubSuscription()
    fun showClubValidationProgress()
    fun hideClubValidationProgress()

}