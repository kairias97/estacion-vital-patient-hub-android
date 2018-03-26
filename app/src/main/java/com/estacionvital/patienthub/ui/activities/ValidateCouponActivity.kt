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
import com.estacionvital.patienthub.presenter.IValidateCouponPresenter
import com.estacionvital.patienthub.presenter.implementations.ValidateCouponPresenterImpl
import com.estacionvital.patienthub.ui.views.IValidateCouponView
import com.estacionvital.patienthub.util.toast
import com.twilio.chat.Channel

class ValidateCouponActivity : BaseActivity(), IValidateCouponView {

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

        supportActionBar!!.title = "Ingrese su cupón"

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
            mValidateCouponPresenter.validateCoupon(coupon)
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

    override fun isValid(isValid: Boolean) {
        mValidateCouponPresenter.createNewExamination(mSpecialty,mTypeChat)
    }

    override fun showCreatingRoomLoading() {
        this.showProgressDialog(getString(R.string.loading_examination_creation))
    }

    override fun getCreatedRoomID(data: String) {
        mValidateCouponPresenter.joinEVTwilioRoom(data)
    }

    override fun prepareToNavigateToChat(data: Channel) {
        navigateToChatWindow(mSpecialty, data.uniqueName)
    }
    private fun navigateToChatWindow(selected: String, room_id: String){
        val intentChatWindow = Intent(this, TwilioChatActivity::class.java)
        intentChatWindow.putExtra("chatType", mTypeChat)
        intentChatWindow.putExtra("specialty", selected)
        intentChatWindow.putExtra("room_id",room_id)
        startActivity(intentChatWindow)
    }
}
