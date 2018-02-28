package com.estacionvital.patienthub.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.RegistrationSession
import com.estacionvital.patienthub.presenter.INumberVerificationPresenter
import com.estacionvital.patienthub.presenter.implementations.NumberVerificationPresenterImpl
import com.estacionvital.patienthub.ui.views.INumberVerificationView

class NumberVerificationActivity : BaseActivity(), INumberVerificationView {



    private lateinit var mNumberVerificationPresenter: INumberVerificationPresenter
    private lateinit var mPhoneEditText : TextInputEditText
    private lateinit var mVerifyButton : Button
    private lateinit var mPhoneInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_verification)

        mNumberVerificationPresenter = NumberVerificationPresenterImpl(this, NetMobileRemoteDataSource.INSTANCE)
        mPhoneEditText = findViewById<TextInputEditText>(R.id.edit_text_phone)
        mVerifyButton = findViewById<Button>(R.id.button_verify)
        mPhoneInputLayout = findViewById<TextInputLayout>(R.id.text_input_layout_phone)

        //Events
        mVerifyButton.setOnClickListener{
            val phone: String = mPhoneEditText.text.toString()
            RegistrationSession.instance.phoneNumber = phone
            mNumberVerificationPresenter.validateNumber(phone)
        }

        mPhoneEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                val phone = p0.toString()
                mNumberVerificationPresenter.checkNumberChanged(phone)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        supportActionBar?.title = getString(R.string.verify_number_activity_title)

    }
    override fun showInvalidNumberMessage() {

        Toast.makeText(this, getString(R.string.not_movistar_msg), Toast.LENGTH_SHORT).show()
    }

    override fun showInternalErrorMessage() {
       Toast.makeText(this, getString(R.string.generic_500_error), Toast.LENGTH_SHORT).show()
    }

    override fun dismissMovistarValidationProgress() {
        hideProgressDialog()
    }

    override fun showNumberRequiredMessage() {
        mPhoneInputLayout.error = getString(R.string.required_input_phone_number)
    }
    override fun hidePhoneNumberInputMessage() {
        mPhoneInputLayout.error = null
    }
    override fun showNumberInputInvalidMessage() {
        mPhoneInputLayout.error = getString(R.string.invalid_input_phone_number)
    }



    override fun showMovistarValidationProgress() {
        showProgressDialog(getString(R.string.validate_movistar_progress))
    }

    override fun showSMSRequestProgress() {
        showProgressDialog(getString(R.string.sms_code_progress))
    }
    override fun dismissSMSRequestProgress() {
        hideProgressDialog()
    }


    override fun navigateToSMSCodeVerification() {
        val smsCodeIntent: Intent = Intent(this, ConfirmationCodeVerificationActivity::class.java)
        smsCodeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //smsCodeIntent.putExtra("phoneNumber", phoneNumber)
        startActivity(smsCodeIntent)
    }

}
