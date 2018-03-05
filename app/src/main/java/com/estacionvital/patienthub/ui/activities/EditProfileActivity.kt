package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.ui.views.IEditProfileView

class EditProfileActivity : AppCompatActivity(), IEditProfileView {

    private lateinit var mButtonCancel: Button
    private lateinit var mButtonAccept: Button
    private lateinit var mTextName: EditText
    private lateinit var mTextLastName: EditText
    private lateinit var mTextEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Editar Perfil"

        mButtonCancel = findViewById<Button>(R.id.edit_profile_cancel)
        mButtonAccept = findViewById<Button>(R.id.edit_profile_accept)
        mTextName = findViewById<EditText>(R.id.edit_text_name_profile)
        mTextLastName = findViewById<EditText>(R.id.edit_text_last_name_profile)
        mTextEmail = findViewById<EditText>(R.id.edit_text_email_profile)

        //setValues
        mTextName.setText(EVUserSession.instance.userProfile.name)
        mTextLastName.setText(EVUserSession.instance.userProfile.last_name)
        mTextEmail.setText(EVUserSession.instance.userProfile.email)

        mButtonAccept.setOnClickListener{
            acceptPressed()
        }
        mButtonCancel.setOnClickListener{
            returnTop()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                returnTop()
            }
        }
        return true
    }

    fun returnTop(){
        NavUtils.navigateUpFromSameTask(this)
    }

    fun acceptPressed(){
        if(EVUserSession.instance.verifyProfileData(mTextName.text.toString(),mTextLastName.text.toString(),mTextEmail.text.toString())){
            returnTop()
        }
        else{

        }
    }
    override fun showLoadingProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
