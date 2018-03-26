package com.estacionvital.patienthub.ui.views

/**
 * Created by dusti on 04/03/2018.
 */
interface IEditProfileView : IBaseView{
    fun showLoadingProgress()
    fun showError()
    fun hideProgress()
    fun goBackToTop()
    fun updateNameInput(name: String)
    fun updateLastNameInput(lastName: String)
}