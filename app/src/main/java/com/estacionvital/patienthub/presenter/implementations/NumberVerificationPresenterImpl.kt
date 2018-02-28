package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.AuthRegistrationCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISendSMSCallback
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.INumberVerificationPresenter
import com.estacionvital.patienthub.ui.views.INumberVerificationView
import com.estacionvital.patienthub.util.AUTH_CREDENTIAL

/**
 * Created by dusti on 24/02/2018.
 */
class NumberVerificationPresenterImpl : INumberVerificationPresenter {
    private val mNumberVerificationView: INumberVerificationView
    private val mNetMobileRemoteDataSource: NetMobileRemoteDataSource

    constructor(numberVerificationView: INumberVerificationView, netMobileRemoteDataSource: NetMobileRemoteDataSource){
        this.mNumberVerificationView = numberVerificationView
        this.mNetMobileRemoteDataSource = netMobileRemoteDataSource
    }

    override fun validateNumber(phoneNumber: String) {
        if (validatePhoneNumberInput(phoneNumber)) {
            mNumberVerificationView.showMovistarValidationProgress()
            mNetMobileRemoteDataSource.verifyNumber(NumberVerificationRequest(phoneNumber, AUTH_CREDENTIAL),
                    object: AuthRegistrationCallback {
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
    private fun requestSMSCode(phoneNumber: String) {
        mNumberVerificationView.showSMSRequestProgress()
        mNetMobileRemoteDataSource.requestSMSCode(SendSMSRequest(phoneNumber, AUTH_CREDENTIAL), object: ISendSMSCallback{
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

    override fun checkNumberChanged(phoneNumber: String) {
        validatePhoneNumberInput(phoneNumber)
    }

}