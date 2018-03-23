package com.estacionvital.patienthubestacionvital.ui.activities

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.NavUtils
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.EVUserSession
import com.estacionvital.patienthubestacionvital.presenter.IEditProfilePresenter
import com.estacionvital.patienthubestacionvital.presenter.implementations.EditProfilePresenterImpl
import com.estacionvital.patienthubestacionvital.ui.views.IEditProfileView

class EditProfileActivity : BaseActivity(), IEditProfileView {
    override fun updateNameInput(name: String) {
        mNameEditText.setText(name)
        mNameEditText.setSelection(mNameEditText.text.toString().length)
    }

    override fun updateLastNameInput(lastName: String) {
        mLastNameEditText.setText(lastName)
        mLastNameEditText.setSelection(mLastNameEditText.text.toString().length)
    }

    private lateinit var mButtonCancel: Button
    private lateinit var mButtonAccept: Button
    private lateinit var mNameEditText: TextInputEditText
    private lateinit var mLastNameEditText: TextInputEditText
    private lateinit var mEmailEditText: TextInputEditText

    private lateinit var mEditProfilePresenter: IEditProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Editar Perfil"

        mButtonCancel = findViewById(R.id.edit_profile_cancel)
        mButtonAccept = findViewById(R.id.edit_profile_accept)
        mNameEditText = findViewById(R.id.edit_text_name_profile)
        mLastNameEditText = findViewById(R.id.edit_text_last_name_profile)
        mEmailEditText = findViewById(R.id.edit_text_email_profile)



        mButtonAccept.setOnClickListener{
            acceptPressed()
        }
        mButtonCancel.setOnClickListener{
            returnTop()
        }

        mEditProfilePresenter = EditProfilePresenterImpl(this, EstacionVitalRemoteDataSource.INSTANCE)

        mNameEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                mEditProfilePresenter.validateNameInput(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        mLastNameEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                mEditProfilePresenter.validateLastNameInput(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        //setValues
        mNameEditText.setText(EVUserSession.instance.userProfile.name)
        mLastNameEditText.setText(EVUserSession.instance.userProfile.last_name)
        mEmailEditText.setText(EVUserSession.instance.userProfile.email)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                returnTop()
            }
        }
        return true
    }

    private fun returnTop(){
        NavUtils.navigateUpFromSameTask(this)
    }

    fun acceptPressed(){
        if(EVUserSession.instance.verifyProfileData(mNameEditText.text.toString(),mLastNameEditText.text.toString(),mEmailEditText.text.toString())){
            returnTop()
        }
        else{
            showLoadingProgress()
            mEditProfilePresenter.updateProfile(mNameEditText.text.toString(), mLastNameEditText.text.toString(), mEmailEditText.text.toString())
        }
    }
    override fun showLoadingProgress() {
        showProgressDialog(getString(R.string.updating_profile_progress))
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.generic_500_error), Toast.LENGTH_SHORT).show()
    }

    override fun hideProgress() {
        hideProgressDialog()
    }
    override fun goBackToTop() {
        returnTop()
    }

}
