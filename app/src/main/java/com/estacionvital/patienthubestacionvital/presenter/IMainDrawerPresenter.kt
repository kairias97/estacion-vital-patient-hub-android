package com.estacionvital.patienthubestacionvital.presenter

/**
 * Created by dusti on 03/03/2018.
 */
interface IMainDrawerPresenter: IBasePresenter {
    fun retrieveEVUSerProfile()
    fun retrieveLocalUserProfileSession()
    fun logout()
}