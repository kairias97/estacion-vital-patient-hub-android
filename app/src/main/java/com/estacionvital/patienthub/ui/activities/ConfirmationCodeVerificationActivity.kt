package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.ui.views.IConfirmationCodeVerificationView

class ConfirmationCodeVerificationActivity : BaseActivity(), IConfirmationCodeVerificationView {
    override fun showCustomMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var mCodeEditText : TextInputEditText
    private lateinit var mVerifyButton : Button
    private lateinit var mCodeInputLayout: TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation_code_verification)
        supportActionBar?.title = getString(R.string.verify_code_activity_title)
        mCodeEditText = findViewById<TextInputEditText>(R.id.edit_text_code)
        mVerifyButton = findViewById<Button>(R.id.button_verify)
        mCodeInputLayout = findViewById<TextInputLayout>(R.id.text_input_layout_code)

        //Bundle extras
        var phoneNumber: String = ""
        if (intent.extras != null) {
            phoneNumber = intent.extras.getString("phoneNumber")
        }

        //Implement here presenter

        //Events
        mVerifyButton.setOnClickListener{
            val phone: String = mCodeEditText.text.toString()
            //mNumberVerificationPresenter.validateNumber(phone)
        }

        mCodeEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val code = p0.toString()
                //mNumberVerificationPresenter.checkNumberChanged(phone)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })
    }

    override fun showCodeRequiredMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideCodeInputMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showInvalidCodeMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showInternalErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCodeValidationProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissCodeValidationProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissEVLoginRequestProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEVLoginRequestProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToMain() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToConfirmSuscription() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
