package com.estacionvital.patienthub.presenter.implementations


import com.estacionvital.patienthub.data.local.SharedPrefManager
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

    private fun validateCodeInput(code: String): Boolean{
        if(code.isNullOrEmpty()) {
            mCodeVerificationView.showCodeRequiredMessage()
            return false
        }
        mCodeVerificationView.hideCodeInputMessage()
        return true
    }
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

                            mCodeVerificationView.navigateToMain()
                        } else {
                            mCodeVerificationView.navigateToConfirmSuscription()
                        }

                    }

                    override fun onFailure() {

                        mCodeVerificationView.dismissEVLoginRequestProgress()
                        mCodeVerificationView.showInternalErrorMessage()
                    }

                })
    }

    override fun checkCodeChanged(confirmationCode: String) {
        validateCodeInput(confirmationCode)
    }
}

