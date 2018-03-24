package com.estacionvital.patienthubestacionvital.ui.activities

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.broadcast.ISMSListener
import com.estacionvital.patienthubestacionvital.broadcast.SMSReceiver
import com.estacionvital.patienthubestacionvital.data.local.SharedPrefManager
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthubestacionvital.presenter.IConfirmationCodePresenter
import com.estacionvital.patienthubestacionvital.presenter.implementations.ConfirmationCodePresenterImpl
import com.estacionvital.patienthubestacionvital.ui.views.IConfirmationCodeVerificationView
import com.estacionvital.patienthubestacionvital.util.toast

class ConfirmationCodeVerificationActivity : BaseActivity(), IConfirmationCodeVerificationView {


    private lateinit var mCodeEditText : TextInputEditText
    private lateinit var mVerifyButton : Button
    private lateinit var mCodeInputLayout: TextInputLayout
    private lateinit var mPresenter: IConfirmationCodePresenter
    private lateinit var mSMSBR: SMSReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation_code_verification)
        supportActionBar?.title = getString(R.string.verify_code_activity_title)
        mCodeEditText = findViewById<TextInputEditText>(R.id.edit_text_code)
        mVerifyButton = findViewById<Button>(R.id.button_verify)
        mCodeInputLayout = findViewById<TextInputLayout>(R.id.text_input_layout_code)


        mPresenter = ConfirmationCodePresenterImpl(this,
                NetMobileRemoteDataSource.INSTANCE,
                EstacionVitalRemoteDataSource.INSTANCE,
                SharedPrefManager(getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)))
        //Implement here presenter

        //Events
        mVerifyButton.setOnClickListener{
            val code: String = mCodeEditText.text.toString()
            mPresenter.validateCode(code)
            //mNumberVerificationPresenter.validateNumber(phone)
        }

        mCodeEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val code = p0.toString()
                mPresenter.checkCodeChanged(code)
                //mNumberVerificationPresenter.checkCodeChanged(phone)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

//Broadcast receiver setup
        mSMSBR = SMSReceiver()
        SMSReceiver.bindListener(object: ISMSListener {
            override fun messageReceived(messageText: String) {
                //Move this logic to presenter
                val re = Regex("[\\D]") //To match non-digit characters
                mCodeEditText.setText(re.replace(messageText, ""))
                mPresenter.validateCode(mCodeEditText.text.toString())
            }

        })
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
        this.registerReceiver(mSMSBR, intentFilter)
    }
    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }
    override fun onDestroy() {
        super.onDestroy()
        this.unregisterReceiver(mSMSBR)


    }
    override fun showCodeRequiredMessage() {
        mCodeInputLayout.error = getString(R.string.required_input_code)

    }

    override fun hideCodeInputMessage() {
        mCodeInputLayout.error = null
    }


    override fun showInternalErrorMessage() {
        this.toast(R.string.generic_500_error)
    }

    override fun showCodeValidationProgress() {
        showProgressDialog(getString(R.string.code_verification_progress))
    }

    override fun dismissCodeValidationProgress() {
        hideProgressDialog()
    }

    override fun dismissEVLoginRequestProgress() {
        hideProgressDialog()
    }

    override fun showEVLoginRequestProgress() {
        showProgressDialog(getString(R.string.ev_login_progress))
    }

    override fun navigateToMain() {
        //val mainIntent: Intent = Intent(this, MainActivity::class.java)\
        val mainIntent: Intent = Intent(this, MainActivityDrawer::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainIntent)
    }

    override fun navigateToConfirmSuscription() {
        val confirmSuscriptionIntent: Intent = Intent(this, SuscriptionQuestionActivity::class.java)
        confirmSuscriptionIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(confirmSuscriptionIntent)
    }

}