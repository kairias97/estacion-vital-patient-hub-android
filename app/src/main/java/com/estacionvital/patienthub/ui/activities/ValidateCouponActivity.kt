package com.estacionvital.patienthub.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.presenter.IValidateCouponPresenter
import com.estacionvital.patienthub.presenter.implementations.ValidateCouponPresenterImpl
import com.estacionvital.patienthub.ui.views.IValidateCouponView
import com.estacionvital.patienthub.util.toast
import com.twilio.chat.Channel

class ValidateCouponActivity : BaseActivity(), IValidateCouponView {
    override fun showInvalidCouponMessage() {
        this.toast(R.string.invalid_coupon_msg)
    }

    private lateinit var mTypeChat: String
    private lateinit var mSpecialty: String
    private lateinit var mValidateCouponPresenter: IValidateCouponPresenter
    private lateinit var mVerifyButton: Button
    private lateinit var mCouponEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate_coupon)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = getString(R.string.title_activity_validate_coupon)

        mValidateCouponPresenter = ValidateCouponPresenterImpl(SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ),this, EstacionVitalRemoteDataSource.INSTANCE, EVTwilioChatRemoteDataSource.instance)
        mVerifyButton = findViewById<Button>(R.id.button_verify)
        mCouponEditText = findViewById<EditText>(R.id.edit_text_coupon)
        if(intent.extras != null){
            mTypeChat = intent.getStringExtra("chatType")
            mSpecialty = intent.getStringExtra("specialty")
        }

        mVerifyButton.setOnClickListener {
            val coupon = mCouponEditText.text.toString()
            mValidateCouponPresenter.validateCoupon(coupon, mSpecialty,mTypeChat)
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
    private fun returnTop(){
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun onBackPressed() {
        returnTop()
    }

    override fun showValidateLoading() {
        this.showProgressDialog(getString(R.string.message_validating_coupon))
    }

    override fun hideLoading() {
        this.hideProgressDialog()
    }

    override fun showErrorLoading() {
        this.toast(getString(R.string.generic_500_error))
    }


    override fun showCreatingRoomLoading() {
        this.showProgressDialog(getString(R.string.loading_examination_creation))
    }



    override fun prepareToNavigateToChat(data: EVChannel) {
        navigateToChatWindow(data)
    }
    private fun navigateToChatWindow(channel:EVChannel){
        val intentChatWindow = Intent(this, TwilioChatActivity::class.java)
        intentChatWindow.putExtra("chatType", channel.type)
        intentChatWindow.putExtra("specialty", channel.specialty)
        intentChatWindow.putExtra("room_id",channel.unique_name)
        intentChatWindow.putExtra("channel", channel)
        startActivity(intentChatWindow)
    }
}
