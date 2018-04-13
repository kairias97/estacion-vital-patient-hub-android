package com.estacionvital.patienthub.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.presenter.ISplashPresenter
import com.estacionvital.patienthub.presenter.implementations.SplashPresenterImpl
import com.estacionvital.patienthub.ui.views.ISplashView

class SplashActivity : BaseActivity(), ISplashView {
    override fun navigateToChat(evChannel: EVChannel) {
        val intentChatWindow = Intent(this, TwilioChatActivity::class.java)
        intentChatWindow.putExtra("channel", evChannel)
        /*
        intentChatWindow.putExtra("specialty", selected)
        intentChatWindow.putExtra("room_id",room_id)*/
        startActivity(intentChatWindow)
    }

    override fun getContext(): Context {
        return this
    }

    private var mSplashPresenter: ISplashPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        mSplashPresenter = SplashPresenterImpl(this,
                SharedPrefManager(
                        getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                                Context.MODE_PRIVATE)
                ),
                EstacionVitalRemoteDataSource.INSTANCE,
                EVTwilioChatRemoteDataSource.instance
        )
        if (intent.extras != null) {

            var channelID: String
            if (intent.hasExtra("notificationChannelID")) {
                channelID = intent.extras.getString("notificationChannelID")
                mSplashPresenter!!.onChatNotificatioReceived(channelID)
            } else {

                mSplashPresenter!!.checkSession()
            }
        } else {

            mSplashPresenter!!.checkSession()
        }
    }

    override fun navigateToNumberVerification() {
        //Implement here logic to navigate to Registration
        val numberVerificationIntent: Intent = Intent(this, NumberVerificationActivity::class.java)
        numberVerificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(numberVerificationIntent)
    }

    override fun navigateToMain() {
        //Implement here logic to navigate to mains
        //val mainNavigationIntent: Intent = Intent(this, MainActivity::class.java)
        val mainNavigationIntent: Intent = Intent(this, MainActivityDrawer::class.java)
        mainNavigationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainNavigationIntent)
    }

}
