package com.estacionvital.patienthub.presenter.implementations

import android.content.Context
import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveProfileCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveUserExaminationsHIstoryCalllback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioClientCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ILogoutCallback
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVRetrieveProfileResponse
import com.estacionvital.patienthub.model.EVRetrieveUserExaminationResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.model.LogoutResponse
import com.estacionvital.patienthub.presenter.IMainDrawerPresenter
import com.estacionvital.patienthub.ui.views.IMainDrawerView

/**
 * Created by dusti on 03/03/2018.
 */
class MainDrawerPresenterImpl: IMainDrawerPresenter {
    override fun expireSession() {
        mSharedPrefManager.clearPreferences()
        mMainDrawerView.showExpirationMessage()
    }

    override fun logout() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mMainDrawerView.showLoggingOutProgress()
        mEstacionVitalRemoteDataSource.logout(token, object: ILogoutCallback{
            override fun onTokenExpired() {
                mMainDrawerView.hideLoggingOutProgress()
                expireSession()
            }

            override fun onSuccess(response: LogoutResponse) {
                mMainDrawerView.hideLoggingOutProgress()
                if (response.status == "success") {
                    mSharedPrefManager.clearPreferences()
                    EVUserSession.instance.phoneNumber = ""
                    EVUserSession.instance.authToken = ""
                    mMainDrawerView.navigateToNumberVerification()
                } else {
                    mMainDrawerView.showError()
                }
            }

            override fun onFailure() {
                mMainDrawerView.hideLoggingOutProgress()
                mMainDrawerView.showError()
            }

        })
    }

    override fun retrieveLocalUserProfileSession() {
        if (EVUserSession.instance.userProfile != null) {
            mMainDrawerView.setDrawerHeaderData( EVUserSession.instance.userProfile)

        }

    }

    private val mMainDrawerView : IMainDrawerView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource

    constructor(mainDrawerView: IMainDrawerView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource,
                sharedPrefManager: SharedPrefManager, evTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource){
        this.mSharedPrefManager = sharedPrefManager
        this.mMainDrawerView = mainDrawerView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDataSource
    }

    override fun retrieveEVUSerProfile(context: Context) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mEstacionVitalRemoteDataSource.retrieveEVUserProfile(token,
                object: IEVRetrieveProfileCallback {
                    override fun onTokenExpired() {
                        expireSession()
                    }

                    override fun onSuccess(result: EVRetrieveProfileResponse) {
                        if(result.status == "success"){
                            EVUserSession.instance.userProfile = result.data
                            getTwilioToken(context)
                            mMainDrawerView.setDrawerHeaderData(EVUserSession.instance.userProfile)
                        }
                        else{
                            mMainDrawerView.showError()
                        }
                    }

                    override fun onFailure() {

                        mMainDrawerView.showError()
                    }
                })
    }

    override fun getTwilioToken(context: Context) {
        mMainDrawerView.showCreatingClientProgress()
        val token = "Token token=${EVUserSession.instance.authToken}"
        mEstacionVitalRemoteDataSource.retrieveExaminationsHistory(token, object: IEVRetrieveUserExaminationsHIstoryCalllback{
            override fun onSuccess(response: EVRetrieveUserExaminationResponse) {
                EVUserSession.instance.twilioToken = response.data.twilio_token
                createEVTwilioChatClient(context)
            }

            override fun onFailure() {
                mMainDrawerView.hideProgressDialog()
                mMainDrawerView.showError()
            }

            override fun onTokenExpired() {
                mMainDrawerView.hideProgressDialog()
                mMainDrawerView.showError()
            }
        })
    }

    override fun createEVTwilioChatClient(context: Context) {
        mEVTwilioChatRemoteDataSource.setupTwilioClient(EVUserSession.instance.twilioToken, context, object: IEVTwilioClientCallback{
            override fun onSuccess() {
                mMainDrawerView.hideProgressDialog()
                mMainDrawerView.chatClientFinished()
            }

            override fun onFailure() {
                mMainDrawerView.hideProgressDialog()
                mMainDrawerView.showError()
            }
        })
    }
}