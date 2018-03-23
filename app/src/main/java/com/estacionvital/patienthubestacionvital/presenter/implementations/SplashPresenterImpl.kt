package com.estacionvital.patienthubestacionvital.presenter.implementations

import com.estacionvital.patienthubestacionvital.data.local.SharedPrefManager
import com.estacionvital.patienthubestacionvital.model.EVUserSession
import com.estacionvital.patienthubestacionvital.presenter.ISplashPresenter
import com.estacionvital.patienthubestacionvital.ui.views.ISplashView
import com.estacionvital.patienthubestacionvital.util.TimedTaskHandler

/**
 * Created by kevin on 22/2/2018.
 */
class SplashPresenterImpl (val splashView: ISplashView, val mSharedPrefManager: SharedPrefManager): ISplashPresenter {
    private val mSplashTimeOut: Int = 1000
    override fun checkSession() {
        //Implement here logic to check session on splash screen
        val timedTask = TimedTaskHandler()
        val token = mSharedPrefManager.getSharedPrefString(SharedPrefManager.PreferenceKeys.AUTH_TOKEN)
        val phoneNumber = mSharedPrefManager.getSharedPrefString(SharedPrefManager.PreferenceKeys.PHONE_NUMBER)
        if ( token != "") {
            EVUserSession.instance.authToken = token
            EVUserSession.instance.phoneNumber = phoneNumber
            timedTask.executeTimedTask({splashView.navigateToMain()}, mSplashTimeOut.toLong())
        } else {
            timedTask.executeTimedTask({splashView.navigateToNumberVerification()}, mSplashTimeOut.toLong())
        }
        //If it is not logged then go to Registration

    }
}