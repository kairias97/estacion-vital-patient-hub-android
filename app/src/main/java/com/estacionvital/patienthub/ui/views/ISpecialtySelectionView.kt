package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.model.EVSpecialtiesResponse
import com.twilio.chat.Channel

/**
 * Created by dusti on 15/03/2018.
 */
interface ISpecialtySelectionView: IBaseView {
    fun showProgressDialog()
    fun showAvailabilityProgressDialog()
    fun showCreatingExaminationProgress()
    fun hideLoading()
    fun showErrorLoading()
    fun setSpecialtiesData(data: EVSpecialtiesResponse)
    fun getDoctorAvailability(data: Boolean)

    fun prepareToNavigateToCoupon(specialty: String, typeChat: String)
    fun prepareToNavigateToCreditCard(specialty: String, typeChat: String)
    fun prepareToNavigateToChat(data: EVChannel)
}