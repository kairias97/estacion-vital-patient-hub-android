package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.ui.views.IConfirmationCodeVerificationView

class ConfirmationCodeVerificationActivity : BaseActivity(), IConfirmationCodeVerificationView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation_code_verification)
        supportActionBar?.title = getString(R.string.verify_code_activity_title)
    }

    override fun showCodeRequiredMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showValidationMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToProfileRegistration() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
