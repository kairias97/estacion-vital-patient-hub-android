package com.estacionvital.patienthub.presenter

import com.estacionvital.patienthub.model.GenderEnum

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