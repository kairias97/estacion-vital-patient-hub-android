package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IEVProfileUpdateCallback
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVProfileUpdateRequest
import com.estacionvital.patienthub.model.EVProfileUpdateResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.IEditProfilePresenter
import com.estacionvital.patienthub.ui.views.IEditProfileView

/**
 * Created by dusti on 05/03/2018.
 */
class EditProfilePresenterImpl: IEditProfilePresenter{

    private val mEditProfileView: IEditProfileView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    constructor(editProfileView: IEditProfileView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource){
        this.mEditProfileView = editProfileView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
    }

    override fun updateProfile(name: String, last_name: String, email: String) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mEstacionVitalRemoteDataSource.updateEVProfile(token, EVProfileUpdateRequest(name, last_name, email),
                object: IEVProfileUpdateCallback{
                    override fun onSuccess(result: EVProfileUpdateResponse) {
                        mEditProfileView.hideProgress()
                        if(result.status == "success"){
                            mEditProfileView.goBackToTop()
                        }
                        else{
                            mEditProfileView.showError()
                        }
                    }

                    override fun onFailure() {
                        mEditProfileView.hideProgress()
                        mEditProfileView.showError()
                    }
                })
    }
}