package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.*
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVCreateNewExaminationResponse
import com.estacionvital.patienthub.model.EVRetrieveDoctorsAvailabilityResponse
import com.estacionvital.patienthub.model.EVSpecialtiesResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.ISpecialtySelectionPresenter
import com.estacionvital.patienthub.ui.views.ISpecialtySelectionView
import com.twilio.chat.Channel
import com.twilio.chat.ChannelDescriptor
import com.twilio.chat.Paginator

/**
 * Created by dusti on 15/03/2018.
 */
class SpecialtySelectionPresenterImpl: ISpecialtySelectionPresenter {
    private val mSpecialtySelectionView: ISpecialtySelectionView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource

    constructor(specialtySelectionView: ISpecialtySelectionView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource, evTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource){
        this.mSpecialtySelectionView = specialtySelectionView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDataSource
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
                mSpecialtySelectionView.getDoctorAvailability(response.available)
            }

            override fun onFailure() {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.showErrorLoading()
            }
        })
    }

    override fun createNewExamination(specialty: String, service_type: String) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mSpecialtySelectionView.showCreatingExaminationProgress()
        mEstacionVitalRemoteDataSource.createNewExamination(token, specialty, service_type, object: IEVCreateNewExaminationCallBack{
            override fun onSuccess(response: EVCreateNewExaminationResponse) {
                mSpecialtySelectionView.getCreatedRoomID(response.room)
            }

            override fun onFailure() {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.showErrorLoading()
            }
        })
    }

    override fun joinEVTwilioRoom(room: String) {
        mEVTwilioChatRemoteDataSource.findChannelByID(room,object: IEVTwilioFindChannelByIDCallback{
            override fun onSuccess(channel: Channel) {
                mEVTwilioChatRemoteDataSource.joinChannel(channel,object: IEVTwilioJoinChannelCallBack{
                    override fun onSuccess() {
                        mSpecialtySelectionView.hideLoading()
                        mSpecialtySelectionView.prepareToNavigateToChat(channel)
                    }

                    override fun onFailure() {
                        mSpecialtySelectionView.hideLoading()
                        mSpecialtySelectionView.showErrorLoading()
                    }
                })
            }

            override fun onFailure() {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.showErrorLoading()
            }
        })
    }

}