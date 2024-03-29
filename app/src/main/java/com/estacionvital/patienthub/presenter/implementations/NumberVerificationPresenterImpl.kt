package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IAuthRegistrationCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISendSMSCallback
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.INumberVerificationPresenter
import com.estacionvital.patienthub.ui.views.INumberVerificationView
import com.estacionvital.patienthub.util.NETMOBILE_AUTH_CREDENTIAL

/**
 * Created by dusti on 24/02/2018.
 */
/**
 * Clase presenter para la verificación de numero movistar
 */
class NumberVerificationPresenterImpl : INumberVerificationPresenter {
    private val mNumberVerificationView: INumberVerificationView
    private val mNetMobileRemoteDataSource: NetMobileRemoteDataSource

    constructor(numberVerificationView: INumberVerificationView, netMobileRemoteDataSource: NetMobileRemoteDataSource){
        this.mNumberVerificationView = numberVerificationView
        this.mNetMobileRemoteDataSource = netMobileRemoteDataSource
    }
    //Verificar input de numero movistar
    override fun validateNumber(phoneNumber: String) {
        if (validatePhoneNumberInput(phoneNumber)) {
            mNumberVerificationView.showMovistarValidationProgress()
            mNetMobileRemoteDataSource.verifyNumber(NumberVerificationRequest(phoneNumber, NETMOBILE_AUTH_CREDENTIAL),
                    object: IAuthRegistrationCallback {
                        override fun onConnectionError() {
                            mNumberVerificationView.dismissMovistarValidationProgress()
                            mNumberVerificationView.showConnectionError()
                        }

                        override fun onSuccess(response: NumberVerificationResponse) {
                            mNumberVerificationView.dismissMovistarValidationProgress()
                            if(response.status==1){
                                //mNumberVerificationView.dismissMovistarValidationProgress()
                                requestSMSCode(phoneNumber)
                                //mNumberVerificationView.navigateToSMSCodeVerification()
                            }
                            else {

                                mNumberVerificationView.showInvalidNumberMessage()
                            }

                        }

                        override fun onFailure() {
                            mNumberVerificationView.dismissMovistarValidationProgress()
                            mNumberVerificationView.showInternalErrorMessage()

                        }

                    })
        }

    }
    //Validar el input de phonenumber
    private fun validatePhoneNumberInput(phoneNumber: String): Boolean{
        if(phoneNumber.isNullOrEmpty()) {
            mNumberVerificationView.showNumberRequiredMessage()
            return false
        }
        if (phoneNumber.length != 8) {
            mNumberVerificationView.showNumberInputInvalidMessage()
            return false
        }
        mNumberVerificationView.hidePhoneNumberInputMessage()
        return true
    }
    //solicitar codigo sms
    private fun requestSMSCode(phoneNumber: String) {
        mNumberVerificationView.showSMSRequestProgress()
        mNetMobileRemoteDataSource.requestSMSCode(SendSMSRequest(phoneNumber, NETMOBILE_AUTH_CREDENTIAL), object: ISendSMSCallback{
            override fun onConnectionError() {
                mNumberVerificationView.dismissSMSRequestProgress()
                mNumberVerificationView.showConnectionError()
            }

            override fun onSuccess(response: SendSMSResponse) {
                mNumberVerificationView.dismissSMSRequestProgress()
                RegistrationSession.instance.phoneNumber = phoneNumber
                mNumberVerificationView.navigateToSMSCodeVerification()
            }

            override fun onFailure() {

                mNumberVerificationView.dismissSMSRequestProgress()
                mNumberVerificationView.showInternalErrorMessage()
            }

        })
    }
    //Para manejar evento de que cambie el numero de telefono
    override fun checkNumberChanged(phoneNumber: String) {
        validatePhoneNumberInput(phoneNumber)
    }

}