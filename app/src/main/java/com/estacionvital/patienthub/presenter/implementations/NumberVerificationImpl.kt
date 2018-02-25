package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.NumberVerification
import com.estacionvital.patienthub.NumberVerificationResponse
import com.estacionvital.patienthub.data.remote.RegistrationRemoteDataSource
import com.estacionvital.patienthub.presenter.INumberVerificationPresenter
import com.estacionvital.patienthub.ui.views.INumberVerificationView
import com.estacionvital.patienthub.util.AUTH_CREDENTIAL

/**
 * Created by dusti on 24/02/2018.
 */
class NumberVerificationImpl: INumberVerificationPresenter {
    private val mNumberVerificationView: INumberVerificationView
    private val mRegistrationRemoteDataSource: RegistrationRemoteDataSource

    constructor(numberVerificationView: INumberVerificationView, registrationRemoteDataSource: RegistrationRemoteDataSource){
        this.mNumberVerificationView = numberVerificationView
        this.mRegistrationRemoteDataSource = registrationRemoteDataSource
    }

    override fun validateNumber(phoneNumber: String) {
        mNumberVerificationView.showMovistarValidationProgress()
        mRegistrationRemoteDataSource.verifyNumber(NumberVerification(phoneNumber, AUTH_CREDENTIAL),
                object:RegistrationRemoteDataSource.AuthRegistrationCallback{
                    override fun onValidNumber(response: NumberVerificationResponse) {
                        if(response.status==1){
                            mNumberVerificationView.dismissMovistarValidationProgress()
                            mNumberVerificationView.navigateToSMSCodeVerification(true)
                        }
                        else if(response.status==-1){
                            mNumberVerificationView.dismissMovistarValidationProgress()
                            mNumberVerificationView.showErrorMessage()
                        }

                    }

                    override fun onWrongNumber() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onVerificationFailed() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }

    override fun checkNumberChanged(phoneNumber: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}