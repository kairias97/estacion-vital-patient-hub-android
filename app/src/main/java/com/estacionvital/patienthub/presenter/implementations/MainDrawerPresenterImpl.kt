package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveProfileCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ILogoutCallback
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVRetrieveProfileResponse
import com.estacionvital.patienthub.model.EVUserProfile
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.model.LogoutResponse
import com.estacionvital.patienthub.presenter.IMainDrawerPresenter
import com.estacionvital.patienthub.ui.views.IMainDrawerView

/**
 * Created by dusti on 03/03/2018.
 */
class MainDrawerPresenterImpl: IMainDrawerPresenter {
    override fun logout() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mMainDrawerView.showLoggingOutProgress()
        mEstacionVitalRemoteDataSource.logout(token, object: ILogoutCallback{
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
        mMainDrawerView.setDrawerHeaderData( EVUserSession.instance.userProfile)
    }

    private val mMainDrawerView : IMainDrawerView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(mainDrawerView: IMainDrawerView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource,
                sharedPrefManager: SharedPrefManager){
        this.mSharedPrefManager = sharedPrefManager
        this.mMainDrawerView = mainDrawerView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
    }

    override fun retrieveEVUSerProfile() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mEstacionVitalRemoteDataSource.retrieveEVUserProfile(token,
                object: IEVRetrieveProfileCallback {
                    override fun onSuccess(result: EVRetrieveProfileResponse) {
                        if(result.status == "success"){
                            EVUserSession.instance.userProfile = result.data
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
}