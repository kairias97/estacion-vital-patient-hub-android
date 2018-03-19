package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveDoctorsAvailabilityCallBack
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveSpecialtiesCallback
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVRetrieveDoctorsAvailabilityResponse
import com.estacionvital.patienthub.model.EVSpecialtiesResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.ISpecialtySelectionPresenter
import com.estacionvital.patienthub.ui.views.ISpecialtySelectionView

/**
 * Created by dusti on 15/03/2018.
 */
class SpecialtySelectionPresenterImpl: ISpecialtySelectionPresenter {
    private val mSpecialtySelectionView: ISpecialtySelectionView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    constructor(specialtySelectionView: ISpecialtySelectionView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource){
        this.mSpecialtySelectionView = specialtySelectionView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
    }
    override fun retrieveSpecialtiesChat() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mSpecialtySelectionView.showProgressDialog()
        mEstacionVitalRemoteDataSource.retrieveSpecialtiesChat(token, object: IEVRetrieveSpecialtiesCallback{
            override fun onSuccess(response: EVSpecialtiesResponse) {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.setSpecialtiesData(response)
            }

            override fun onFailure() {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.showErrorLoading()
            }
        })
    }
    override fun retrieveDoctorAvailability(specialty: String) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mSpecialtySelectionView.showAvailabilityProgressDialog()
        mEstacionVitalRemoteDataSource.retrieveDoctorsAvailability(token, specialty, object: IEVRetrieveDoctorsAvailabilityCallBack{
            override fun onSuccess(response: EVRetrieveDoctorsAvailabilityResponse) {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.getDoctorAvailability(response.available)
            }

            override fun onFailure() {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.showErrorLoading()
            }
        })
    }

}