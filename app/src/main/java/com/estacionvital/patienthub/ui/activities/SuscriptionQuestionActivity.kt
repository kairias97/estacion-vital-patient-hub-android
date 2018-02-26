package com.estacionvital.patienthub.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.ui.views.ISuscriptionQuestionActivity

class SuscriptionQuestionActivity : BaseActivity(), ISuscriptionQuestionActivity {

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
        val clubSuscriptionIntent: Intent = Intent(this, ClubSuscriptionActivity::class.java)
        clubSuscriptionIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(clubSuscriptionIntent)
    }

    override fun closeApp() {
        finish()
    }
}
