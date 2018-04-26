package com.estacionvital.patienthub.presenter

import android.content.Context

/**
 * Created by dusti on 03/03/2018.
 */
interface IMainDrawerPresenter: IBasePresenter {
    fun retrieveEVUSerProfile(context: Context)
    fun retrieveLocalUserProfileSession()
    fun createEVTwilioChatClient(context: Context)
    fun getTwilioToken(context: Context)
    fun logout()
}