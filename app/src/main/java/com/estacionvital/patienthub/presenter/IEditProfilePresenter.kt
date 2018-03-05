package com.estacionvital.patienthub.presenter

/**
 * Created by dusti on 05/03/2018.
 */
interface IEditProfilePresenter {
    fun updateProfile(name: String, last_name: String, email: String)
}