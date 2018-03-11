package com.estacionvital.patienthub.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.presenter.ISplashPresenter
import com.estacionvital.patienthub.presenter.implementations.SplashPresenterImpl
import com.estacionvital.patienthub.ui.views.ISplashView
import com.squareup.picasso.Picasso

class SplashActivity : BaseActivity(), ISplashView {

    private var mSplashPresenter: ISplashPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        mSplashPresenter = SplashPresenterImpl(this,
                SharedPrefManager(
                        getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                                Context.MODE_PRIVATE)
                )
        )
        mSplashPresenter!!.checkSession()
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
