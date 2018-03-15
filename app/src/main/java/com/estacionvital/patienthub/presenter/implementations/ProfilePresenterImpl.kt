package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveProfileCallback
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVRetrieveProfileResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.IProfilePresenter
import com.estacionvital.patienthub.ui.fragmentViews.IProfileFragmentView

/**
 * Created by dusti on 03/03/2018.
 */
class ProfilePresenterImpl: IProfilePresenter {

    private val mProfileView: IProfileFragmentView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    constructor(profileView: IProfileFragmentView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource){
        this.mProfileView = profileView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
    }

    override fun retrieveEVUserProfile() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mProfileView.showLoadingProgress()
        mEstacionVitalRemoteDataSource.retrieveEVUserProfile(token,
                object: IEVRetrieveProfileCallback{
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