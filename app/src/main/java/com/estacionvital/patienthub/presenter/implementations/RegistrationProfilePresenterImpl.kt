package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRegistrationSubmittedCallback
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.IRegistrationProfilePresenter
import com.estacionvital.patienthub.ui.views.IRegistrationProfileView
import com.estacionvital.patienthub.util.EV_AUTH_CREDENTIAL
import com.estacionvital.patienthub.util.RegexUtil

/**
 * Created by kevin on 28/2/2018.
 */
class RegistrationProfilePresenterImpl: IRegistrationProfilePresenter{
    override fun validateName(name: String) {
        if (RegexUtil.instance.containsDigits(name)) {
            mRegistrationProfileView.updateNameInput(RegexUtil.instance.trimNumbersFromString(name))
        }
    }

    override fun validateLastName(lastName: String) {
        if (RegexUtil.instance.containsDigits(lastName)) {
            mRegistrationProfileView.updateLastNameInput(RegexUtil.instance.trimNumbersFromString(lastName))
        }
    }


    private val mSharedPrefManager: SharedPrefManager
    private val mRegistrationProfileView: IRegistrationProfileView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    constructor(sharedPrefManager: SharedPrefManager, view: IRegistrationProfileView,
                evDataSource: EstacionVitalRemoteDataSource){
        this.mSharedPrefManager = sharedPrefManager
        this.mRegistrationProfileView = view
        this.mEstacionVitalRemoteDataSource = evDataSource
    }

    override fun registerEVAccount(name: String, lastName: String, email: String, birthDate: String,
                                   gender: GenderEnum) {
        //Perform logic of webservice consume
        if (validateRegistrationData(name, lastName, email, birthDate)) {
            val isMale:String = if (gender == GenderEnum.male) "1" else "0"
            submitEVRegistration(name, lastName, email,
                    birthDate, isMale)
        }
    }

    private fun submitEVRegistration(name: String, lastName: String, email: String,
                                     birthDate: String, isMale: String){
        val basicAuth:String = "Basic $EV_AUTH_CREDENTIAL"
        mRegistrationProfileView.showRegistrationRequestProgress()
        this.mEstacionVitalRemoteDataSource.submitEVRegistration(basicAuth,
                EVRegistrationRequest(name, lastName, email, birthDate,
                        RegistrationSession.instance.phoneNumber,
                        isMale), object: IEVRegistrationSubmittedCallback{
            override fun onSuccess(response: EVRegistrationResponse) {
                mRegistrationProfileView.hideRegistrationRequestProgress()
                if (response.status == "success"){
                    //Token retrieval and shared preference storage
                    val authToken: String = response.data[0].auth_token
                    mSharedPrefManager.saveString(SharedPrefManager.PreferenceKeys.AUTH_TOKEN,
                            authToken)
                    mSharedPrefManager.saveString(SharedPrefManager.PreferenceKeys.PHONE_NUMBER,
                            RegistrationSession.instance.phoneNumber
                            )
                    EVUserSession.instance.authToken = authToken
                    EVUserSession.instance.phoneNumber = RegistrationSession.instance.phoneNumber
                    mRegistrationProfileView.showRegistrationSuccessMessage()
                    mRegistrationProfileView.navigateToMain()
                } else {
                    //There is not standard response at the moment
                    mRegistrationProfileView.showCustomWSMessage("")
                }
            }

            override fun onFailure() {
                mRegistrationProfileView.hideRegistrationRequestProgress()
                mRegistrationProfileView.showInternalServerErrorMessage()
            }

        })
    }
    override fun retrievePhoneNumber(): String {
        return RegistrationSession.instance.phoneNumber
    }
    private fun validateRegistrationData(name: String, lastName: String,
                                         email: String, birthDate: String): Boolean{
        var isDataValid = true
        if (name.isNullOrEmpty()){
            mRegistrationProfileView.showNameRequiredMessage()
            isDataValid = false
        } else {
            mRegistrationProfileView.clearNameInputMessage()

        }
        if (lastName.isNullOrEmpty()){
            mRegistrationProfileView.showLastNameRequiredMessage()
            isDataValid = false
        } else{
            mRegistrationProfileView.clearLastNameInputMessage()
        }
        if (email.isNullOrEmpty()){
            mRegistrationProfileView.showEmailRequiredMessage()
            isDataValid = false
        } else if (!RegexUtil.instance.isEmailValid(email)){
            mRegistrationProfileView.showInvalidEmailMessage()
            isDataValid = false
        } else {
            mRegistrationProfileView.clearEmailInputMessage()
        }
        if (birthDate.isNullOrEmpty()){
            mRegistrationProfileView.showBirthDateRequiredMessage()
            isDataValid = false
        } else {
            mRegistrationProfileView.clearBirthDateInputMessage()

        }
        if(!isDataValid) {
            mRegistrationProfileView.showInvalidRegistrationDataMessage()
        }
        return isDataValid

    }


}