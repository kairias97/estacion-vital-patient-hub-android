package com.estacionvital.patienthubestacionvital.ui.views

/**
 * Created by dusti on 04/03/2018.
 */
interface IEditProfileView {
    fun showLoadingProgress()
    fun showError()
    fun hideProgress()
    fun goBackToTop()
    fun updateNameInput(name: String)
    fun updateLastNameInput(lastName: String)
}