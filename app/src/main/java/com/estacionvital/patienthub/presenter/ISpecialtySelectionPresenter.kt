package com.estacionvital.patienthub.presenter

/**
 * Created by dusti on 15/03/2018.
 */
interface ISpecialtySelectionPresenter: IBasePresenter {
    fun retrieveSpecialtiesChat()
    fun retrieveDoctorAvailability(specialty: String)
    fun createNewExamination(specialty: String, service_type: String)
    //fun joinEVTwilioRoom(room: String)
}