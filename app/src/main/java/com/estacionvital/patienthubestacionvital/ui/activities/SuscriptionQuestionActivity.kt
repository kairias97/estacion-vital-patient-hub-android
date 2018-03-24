package com.estacionvital.patienthubestacionvital.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.ui.views.ISuscriptionQuestionView

class SuscriptionQuestionActivity : BaseActivity(), ISuscriptionQuestionView {

    private lateinit var mConfirmSuscriptionBtn: Button
    private lateinit var mCancelSuscriptionBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suscription_question)

        //setting buttons
        mConfirmSuscriptionBtn = findViewById<Button>(R.id.button_accept)
        mCancelSuscriptionBtn = findViewById<Button>(R.id.button_cancel)

        //events
        mConfirmSuscriptionBtn.setOnClickListener{
            navigateToClubSuscription()
        }
        mCancelSuscriptionBtn.setOnClickListener{
            closeApp()
        }
    }

    override fun navigateToClubSuscription() {
        val clubSuscriptionIntent: Intent = Intent(this, ClubSubscriptionActivity::class.java)
        clubSuscriptionIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(clubSuscriptionIntent)
    }

    override fun closeApp() {
        finish()
    }
}
