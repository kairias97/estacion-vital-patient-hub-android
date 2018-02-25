package com.estacionvital.patienthub.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.RegistrationRemoteDataSource
import com.estacionvital.patienthub.presenter.INumberVerificationPresenter
import com.estacionvital.patienthub.presenter.implementations.NumberVerificationImpl
import com.estacionvital.patienthub.ui.views.INumberVerificationView
import com.estacionvital.patienthub.util.TimedTaskHandler

class NumberVerificationActivity : BaseActivity(), INumberVerificationView {
    private lateinit var mNumberVerificationPresenter: INumberVerificationPresenter
    private lateinit var mPhoneEditText : EditText
    private lateinit var mVerifyButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_verification)

        mNumberVerificationPresenter = NumberVerificationImpl(this, RegistrationRemoteDataSource.instance)
        mPhoneEditText = findViewById<EditText>(R.id.editText)
        mVerifyButton = findViewById<Button>(R.id.button)

        //Events
        mVerifyButton.setOnClickListener{
            val phone: String = mPhoneEditText.text.toString()
            mNumberVerificationPresenter.validateNumber(phone)
        }
        //supportActionBar?.hide()

        supportActionBar?.title = getString(R.string.verify_number_activity_title)
        //Setup toolbar

        //Simulation of loading via the next to lines
        showProgressDialog()
        TimedTaskHandler().executeTimedTask({  hideProgressDialog() }, 3000)
    }

    override fun dismissMovistarValidationProgress() {
        hideProgressDialog()
    }

    override fun showNumberRequiredMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showValidationMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorMessage() {
        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
    }


    override fun showMovistarValidationProgress() {
        showProgressDialog()
    }

    override fun showUserVerificationProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToSMSCodeVerification(isRegisteredUser: Boolean) {
        val mainNavigationIntent: Intent = Intent(this, ConfirmationCodeVerificationActivity::class.java)
        mainNavigationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainNavigationIntent)
    }

}
