package com.estacionvital.patienthubestacionvital.presenter

import com.estacionvital.patienthubestacionvital.model.GenderEnum

/**
 * Created by kevin on 28/2/2018.
 */
interface IRegistrationProfilePresenter {
    fun registerEVAccount(name: String,
                          lastName: String,
                          email: String,
                          birthDate: String,
                          gender: GenderEnum
                          )
    fun retrievePhoneNumber(): String
    fun validateName(name:String)
    fun validateLastName(lastName: String)
}