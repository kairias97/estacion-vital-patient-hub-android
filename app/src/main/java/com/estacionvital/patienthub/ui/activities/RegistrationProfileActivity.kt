package com.estacionvital.patienthub.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.widget.Button
import android.widget.RadioGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.GenderEnum
import com.estacionvital.patienthub.presenter.IRegistrationProfilePresenter
import com.estacionvital.patienthub.presenter.implementations.RegistrationProfilePresenterImpl
import com.estacionvital.patienthub.ui.views.IRegistrationProfileView
import com.estacionvital.patienthub.util.toast
import kotlinx.android.synthetic.main.activity_registration_profile.*

class RegistrationProfileActivity : BaseActivity(), IRegistrationProfileView {
    override fun navigateToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainIntent)
    }

    override fun showInvalidRegistrationDataMessage() {
        this.toast(R.string.invalid_registration_data)
    }

    override fun clearNameInputMessage() {
        this.text_input_layout_name.error = null
    }

    override fun clearLastNameInputMessage() {
        this.text_input_layout_last_name.error = null
    }

    override fun clearEmailInputMessage() {
        this.text_input_layout_email.error = null
    }

    override fun clearBirthDateInputMessage() {
        this.text_input_layout_birth_date.error = null
    }

    override fun showInvalidEmailMessage() {
        this.text_input_layout_email.error = getString(R.string.validation_invalid_email)
    }



    private lateinit var mPresenter: IRegistrationProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration_profile)
        ButterKnife.bind(this)
        mPresenter = RegistrationProfilePresenterImpl(SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)),
                this, EstacionVitalRemoteDataSource.INSTANCE)
        supportActionBar!!.title = getString(R.string.registration_profile_activity_title)
        edit_text_phone_number.setText(mPresenter.retrievePhoneNumber())
        registration_profile_cancel.setOnClickListener { finish() }
        registration_profile_accept.setOnClickListener {
            val name = edit_text_name.text.toString()
            val lastName = edit_text_last_name.text.toString()
            val email = edit_text_email.text.toString()
            val birthDate = edit_text_birthDate.text.toString()
            val gender: GenderEnum = when (radio_group_gender.checkedRadioButtonId) {
                R.id.radio_button_male -> GenderEnum.male
                else -> GenderEnum.female
            }
            mPresenter.registerEVAccount(name, lastName, email,birthDate, gender)
        }



    }

    override fun setPhoneNumberUI(phoneNumber: String) {
        this.edit_text_phone_number.setText(phoneNumber)
    }

    override fun showNameRequiredMessage() {
        this.text_input_layout_name.error = getString(R.string.required_input_name)

    }

    override fun showLastNameRequiredMessage() {
        this.text_input_layout_last_name.error = getString(R.string.required_input_last_name)
    }

    override fun showEmailRequiredMessage() {
        this.text_input_layout_email.error = getString(R.string.required_input_email)
    }

    override fun showBirthDateRequiredMessage() {
        this.text_input_layout_birth_date.error = getString(R.string.required_input_birth_date)
    }

    override fun showRegistrationRequestProgress() {
        showProgressDialog(getString(R.string.registration_progress))
    }

    override fun hideRegistrationRequestProgress() {
        hideProgressDialog()
    }

    override fun showInternalServerErrorMessage() {
        this.toast(R.string.generic_500_error)
    }

    override fun showCustomWSMessage(msg: String) {
        this.toast(getString(R.string.custom_ws_message).format(msg))
    }

    override fun showRegistrationSuccessMessage() {
        this.toast(R.string.success_ev_registration_msg)
    }
}
