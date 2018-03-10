package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveProfileCallback
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVRetrieveProfileResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.IMainDrawerPresenter
import com.estacionvital.patienthub.ui.views.IMainDrawerView

/**
 * Created by dusti on 03/03/2018.
 */
class MainDrawerPresenterImpl: IMainDrawerPresenter {
    override fun retrieveLocalUserProfileSession() {
        mMainDrawerView.setDrawerHeaderData( EVUserSession.instance.userProfile)
    }

    private val mMainDrawerView : IMainDrawerView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    constructor(mainDrawerView: IMainDrawerView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource){
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