package com.estacionvital.patienthub.presenter.implementations


import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionActiveCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IValidateEVCredentialsCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IValidatePinCallback
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.IConfirmationCodePresenter
import com.estacionvital.patienthub.ui.views.IConfirmationCodeVerificationView
import com.estacionvital.patienthub.util.NETMOBILE_AUTH_CREDENTIAL
import com.estacionvital.patienthub.util.toBase64


/**
 * Created by kevin on 25/2/2018.
 */
//Presenter para confirmacion de codigo
class ConfirmationCodePresenterImpl: IConfirmationCodePresenter {
    private val mCodeVerificationView: IConfirmationCodeVerificationView
    private val mNetMobileRemoteDataSource: NetMobileRemoteDataSource
    private val mEstacionVitalRemoteDataSource:EstacionVitalRemoteDataSource
    private val mPrefManager:SharedPrefManager



    constructor(numberVerificationView: IConfirmationCodeVerificationView, netMobileRemoteDataSource: NetMobileRemoteDataSource, evRemoteDataSource: EstacionVitalRemoteDataSource, prefManager: SharedPrefManager){
        this.mCodeVerificationView = numberVerificationView
        this.mNetMobileRemoteDataSource = netMobileRemoteDataSource
        this.mEstacionVitalRemoteDataSource = evRemoteDataSource
        this.mPrefManager = prefManager

    }
    //Validar input de UI
    private fun validateCodeInput(code: String): Boolean{
        if(code.isNullOrEmpty()) {
            mCodeVerificationView.showCodeRequiredMessage()
            return false
        }
        mCodeVerificationView.hideCodeInputMessage()
        return true
    }
    //Validar codigo en ws
    override fun validateCode(confirmationCode: String) {
        val phoneNumber: String = RegistrationSession.instance.phoneNumber

        if(validateCodeInput(confirmationCode)) {
            mCodeVerificationView.showCodeValidationProgress()
            mNetMobileRemoteDataSource.validatePinCode(ValidatePinRequest(phoneNumber,
                    confirmationCode, NETMOBILE_AUTH_CREDENTIAL), object: IValidatePinCallback {
                override fun onSuccess(response: ValidatePinResponse) {
                    mCodeVerificationView.dismissCodeValidationProgress()
                    if (response.status == 1) {
                        validateEVLogin(phoneNumber)
                    } else {
                        mCodeVerificationView.showCustomMessage(response.msg)
                    }
                }

                override fun onFailure() {
                    mCodeVerificationView.dismissCodeValidationProgress()
                    mCodeVerificationView.showInternalErrorMessage()
                }

            })
        }
    }

    //Function called after validation successfully ev login
    private fun validateActiveClubSuscriptions(phoneNumber: String) {
        mCodeVerificationView.showClubValidationProgress()
        mNetMobileRemoteDataSource.retrieveSubscriptionActive(SuscriptionActiveRequest(phoneNumber, NETMOBILE_AUTH_CREDENTIAL),
                object: ISuscriptionActiveCallback {
                    override fun onSuccess(response: List<SuscriptionActiveResponse>) {
                        mCodeVerificationView.hideClubValidationProgress()
                        if (response.isNotEmpty()) {
                            mCodeVerificationView.navigateToMain()
                        } else {
                            mCodeVerificationView.navigateToClubSuscription()
                        }
                    }

                    override fun onFailure() {
                        mCodeVerificationView.hideClubValidationProgress()
                        //Change later on for a view message from resources
                        mCodeVerificationView.showCustomMessage("Error al validar suscripciones activas de estación vital")
                    }

                })
        mCodeVerificationView.navigateToMain()
    }
    //Validar login de ev
    private fun validateEVLogin(phoneNumber: String) {
        mCodeVerificationView.showEVLoginRequestProgress()
        mEstacionVitalRemoteDataSource.validateEVCredentials(LoginRequest(phoneNumber.toBase64().replace("\n", "")),
                object:IValidateEVCredentialsCallback {
                    override fun onSuccess(response: LoginResponse) {
                        mCodeVerificationView.dismissEVLoginRequestProgress()
                        if (response.status == "success") {
                            val authToken = response.data[0].auth_token

                            mPrefManager.saveString(SharedPrefManager.PreferenceKeys.AUTH_TOKEN,
                                    authToken)
                            mPrefManager.saveString(SharedPrefManager.PreferenceKeys.PHONE_NUMBER,
                                    phoneNumber)
                            //We prepare the session for main activity
                            EVUserSession.instance.authToken = authToken
                            EVUserSession.instance.phoneNumber = phoneNumber
                            validateActiveClubSuscriptions(phoneNumber)

                        } else {
                            response
                            mCodeVerificationView.navigateToConfirmSuscription()
                        }

                    }

                    override fun onFailure() {

                        mCodeVerificationView.dismissEVLoginRequestProgress()
                        mCodeVerificationView.showInternalErrorMessage()
                    }

                })
    }
    //verificar cambios en el codigo de confirmacion
    override fun checkCodeChanged(confirmationCode: String) {
        validateCodeInput(confirmationCode)
    }
}

