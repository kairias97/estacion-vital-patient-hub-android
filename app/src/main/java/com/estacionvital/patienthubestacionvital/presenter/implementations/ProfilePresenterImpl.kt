package com.estacionvital.patienthubestacionvital.presenter.implementations

import com.estacionvital.patienthubestacionvital.data.local.SharedPrefManager
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IEVRetrieveProfileCallback
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.EVRetrieveProfileResponse
import com.estacionvital.patienthubestacionvital.model.EVUserSession
import com.estacionvital.patienthubestacionvital.presenter.IProfilePresenter
import com.estacionvital.patienthubestacionvital.ui.fragmentViews.IProfileFragmentView

/**
 * Created by dusti on 03/03/2018.
 */
class ProfilePresenterImpl: IProfilePresenter {
    override fun expireSession() {
        mSharedPrefManager.clearPreferences()
        mProfileView.showExpirationMessage()
    }

    private val mProfileView: IProfileFragmentView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(sharedPrefManager: SharedPrefManager,profileView: IProfileFragmentView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource){
        this.mProfileView = profileView
        this.mSharedPrefManager = sharedPrefManager
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
    }

    override fun retrieveEVUserProfile() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mProfileView.showLoadingProgress()
        mEstacionVitalRemoteDataSource.retrieveEVUserProfile(token,
                object: IEVRetrieveProfileCallback{
                    override fun onTokenExpired() {
                        expireSession()
                        mProfileView.showExpirationMessage()
                    }

                    override fun onSuccess(result: EVRetrieveProfileResponse) {
                        mProfileView.hideLoadingProgress()
                        if(result.status == "success"){
                            mProfileView.setProfileData(result.data)
                        }
                        else{
                            mProfileView.showErrorLoading()
                        }
                    }

                    override fun onFailure() {
                        mProfileView.hideLoadingProgress()
                        mProfileView.showErrorLoading()
                    }
                })
    }

}