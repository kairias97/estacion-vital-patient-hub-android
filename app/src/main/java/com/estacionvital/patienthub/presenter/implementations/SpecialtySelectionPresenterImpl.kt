package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.*
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.ISpecialtySelectionPresenter
import com.estacionvital.patienthub.ui.adapters.EVClubViewHolder
import com.estacionvital.patienthub.ui.views.ISpecialtySelectionView
import com.twilio.chat.Channel

/**
 * Created by dusti on 15/03/2018.
 */
class SpecialtySelectionPresenterImpl: ISpecialtySelectionPresenter {
    override fun expireSession() {
        mSharedPrefManager.clearPreferences()
        mSpecialtySelectionView.showExpirationMessage()
    }

    private val mSpecialtySelectionView: ISpecialtySelectionView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(sharedPrefManager: SharedPrefManager, specialtySelectionView: ISpecialtySelectionView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource, evTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource){
        this.mSpecialtySelectionView = specialtySelectionView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }
    override fun retrieveSpecialtiesChat() {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mSpecialtySelectionView.showProgressDialog()
        mEstacionVitalRemoteDataSource.retrieveSpecialtiesChat(token, object: IEVRetrieveSpecialtiesCallback{
            override fun onTokenExpired() {
                mSpecialtySelectionView.hideLoading()
                expireSession()
            }

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
            override fun onTokenExpired() {
                mSpecialtySelectionView.hideLoading()
                expireSession()
            }

            override fun onSuccess(response: EVRetrieveDoctorsAvailabilityResponse) {
                mSpecialtySelectionView.getDoctorAvailability(response.available)
            }

            override fun onFailure() {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.showErrorLoading()
            }
        })
    }

    override fun createNewExamination(specialty: String, service_type: String, type: String, code: String, order_id: String) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mSpecialtySelectionView.showCreatingExaminationProgress()
        mEstacionVitalRemoteDataSource.createNewExamination(token, specialty, service_type, type, code, order_id, object: IEVCreateNewExaminationCallBack{
            override fun onChatCreationDenied() {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.showNoFreeChatAvailable()
            }

            override fun onTokenExpired() {
                mSpecialtySelectionView.hideLoading()
                expireSession()
            }

            override fun onSuccess(response: EVCreateNewExaminationResponse) {
                mSpecialtySelectionView.hideLoading()
                joinEVTwilioRoom(response.room, specialty, service_type)
            }

            override fun onFailure() {
                mSpecialtySelectionView.hideLoading()
                mSpecialtySelectionView.showErrorLoading()
            }
        })
    }

    private fun joinEVTwilioRoom(room: String, specialty: String, serviceType: String) {

        mEVTwilioChatRemoteDataSource.findChannelByID(room,object: IEVTwilioFindChannelByIDCallback{
            override fun onSuccess(channel: Channel) {
                mEVTwilioChatRemoteDataSource.joinChannel(channel,object: IEVTwilioJoinChannelCallBack{
                    override fun onSuccess() {
                        val evChannel = EVChannel()
                        evChannel.unique_name = room
                        evChannel.specialty = specialty
                        evChannel.isFinished = false
                        evChannel.doctorName = ""
                        evChannel.type = serviceType
                        mSpecialtySelectionView.hideLoading()
                        evChannel.twilioChannel = channel
                        mSpecialtySelectionView.prepareToNavigateToChat(evChannel)
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