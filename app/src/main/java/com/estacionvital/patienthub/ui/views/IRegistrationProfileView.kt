package com.estacionvital.patienthub.ui.views

/**
 * Created by kevin on 28/2/2018.
 */
interface IRegistrationProfileView {
    //Required Messages
    fun showNameRequiredMessage()
    fun showLastNameRequiredMessage()
    fun showEmailRequiredMessage()
    fun showBirthDateRequiredMessage()
    fun showInvalidEmailMessage()
    fun clearNameInputMessage()
    fun clearLastNameInputMessage()
    fun clearEmailInputMessage()
    fun clearBirthDateInputMessage()

    //API calls
    fun showRegistrationRequestProgress()
    fun hideRegistrationRequestProgress()
    fun showInternalServerErrorMessage()
    fun showCustomWSMessage(msg: String)
    fun showRegistrationSuccessMessage()
    //UI Behaviour
    fun setPhoneNumberUI(phoneNumber: String)

    fun showInvalidRegistrationDataMessage()
    fun navigateToMain()

    fun updateNameInput(name: String)
    fun updateLastNameInput(lastName: String)


}