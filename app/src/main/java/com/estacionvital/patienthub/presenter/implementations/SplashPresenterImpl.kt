package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.presenter.ISplashPresenter
import com.estacionvital.patienthub.ui.views.ISplashView
import com.estacionvital.patienthub.util.TimedTaskHandler

/**
 * Created by kevin on 22/2/2018.
 */
class SplashPresenterImpl (val splashView: ISplashView): ISplashPresenter {
    private val SPLASH_TIME_OUT: Int = 1000
    override fun checkSession() {
        //Implement here logic to check session on splash screen
        val timedTask : TimedTaskHandler = TimedTaskHandler()
        //If it is not logged then go to Registration
        timedTask.executeTimedTask({splashView.navigateToNumberVerification()}, SPLASH_TIME_OUT.toLong())
    }
}