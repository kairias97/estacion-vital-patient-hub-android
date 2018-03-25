package com.estacionvital.patienthubestacionvital.ui.views

import com.estacionvital.patienthubestacionvital.model.EVSpecialtiesResponse
import com.twilio.chat.Channel

/**
 * Created by dusti on 15/03/2018.
 */
interface ISpecialtySelectionView {
    fun showProgressDialog()
    fun showAvailabilityProgressDialog()
    fun showCreatingExaminationProgress()
    fun hideLoading()
    fun showErrorLoading()
    fun setSpecialtiesData(data: EVSpecialtiesResponse)
    fun getDoctorAvailability(data: Boolean)
    fun getCreatedRoomID(data: String)
    fun prepareToNavigateToCoupon(specialty: String, typeChat: String)
    fun prepareToNavigateToCreditCard(specialty: String, typeChat: String)
    fun prepareToNavigateToChat(data: Channel)
}