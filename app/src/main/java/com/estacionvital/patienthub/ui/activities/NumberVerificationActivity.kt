package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.ui.views.INumberVerificationView
import com.estacionvital.patienthub.util.TimedTaskHandler

class NumberVerificationActivity : BaseActivity(), INumberVerificationView {
    override fun showNumberRequiredMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showValidationMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun showMovistarValidationProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserVerificationProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToSMSCodeVerification(isRegisteredUser: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_verification)
        //supportActionBar?.hide()

        supportActionBar?.title = getString(R.string.verify_number_activity_title)
        //Setup toolbar

        //Simulation of loading via the next to lines
        showProgressDialog()
        TimedTaskHandler().executeTimedTask({  hideProgressDialog() }, 3000)
    }

}
