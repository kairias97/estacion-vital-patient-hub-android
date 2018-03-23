package com.estacionvital.patienthubestacionvital.presenter.implementations

import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.IEVProfileUpdateCallback
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.EVProfileUpdateRequest
import com.estacionvital.patienthubestacionvital.model.EVProfileUpdateResponse
import com.estacionvital.patienthubestacionvital.model.EVUserSession
import com.estacionvital.patienthubestacionvital.presenter.IEditProfilePresenter
import com.estacionvital.patienthubestacionvital.ui.views.IEditProfileView
import com.estacionvital.patienthubestacionvital.util.RegexUtil

/**
 * Created by dusti on 05/03/2018.
 */
class EditProfilePresenterImpl: IEditProfilePresenter{


    private val mEditProfileView: IEditProfileView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    override fun validateNameInput(name: String) {
        if (RegexUtil.instance.containsDigits(name)) {
            mEditProfileView.updateNameInput(RegexUtil.instance.trimNumbersFromString(name))
        }
    }

    override fun validateLastNameInput(lastName: String) {
        if (RegexUtil.instance.containsDigits(lastName)) {
            mEditProfileView.updateLastNameInput(RegexUtil.instance.trimNumbersFromString(lastName))
        }
    }
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
                            EVUserSession.instance.userProfile.email = email
                            EVUserSession.instance.userProfile.last_name = last_name
                            EVUserSession.instance.userProfile.name = name

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