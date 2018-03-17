package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import com.estacionvital.patienthub.R

class TwilioChatActivity : BaseActivity() {

    private lateinit var mTypeChat: String
    private lateinit var mSpecialtySelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twilio_chat)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Chat"

        if (intent.extras != null) {
            mTypeChat = intent.extras.getString("chatType")
            mSpecialtySelected = intent.extras.getString("specialtySelected")
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

    override fun onBackPressed() {
        returnTop()
    }
    private fun returnTop(){
        NavUtils.navigateUpFromSameTask(this)
    }
}
