package com.estacionvital.patienthubestacionvital.presenter

/**
 * Created by dusti on 15/03/2018.
 */
interface ISpecialtySelectionPresenter {
    fun retrieveSpecialtiesChat()
    fun retrieveDoctorAvailability(specialty: String)
    fun createNewExamination(specialty: String, service_type: String)
    fun joinEVTwilioRoom(room: String)
}