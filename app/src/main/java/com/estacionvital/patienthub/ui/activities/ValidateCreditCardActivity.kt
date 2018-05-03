package com.estacionvital.patienthub.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.*
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.presenter.IValidateCreditCardPresenter
import com.estacionvital.patienthub.presenter.implementations.ValidateCreditCardPresenterImpl
import com.estacionvital.patienthub.ui.views.IValidateCreditCardView
import com.estacionvital.patienthub.util.CONSULTANCE_PRICE
import com.estacionvital.patienthub.util.toast
import kotlinx.android.synthetic.main.activity_validate_credit_card.*
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList

class ValidateCreditCardActivity : BaseActivity(), IValidateCreditCardView {

    private lateinit var mHolderCreditCard: EditText
    private lateinit var mNumberCreditCard: EditText
    private lateinit var mExpMonthCreditCard: Spinner
    private lateinit var mExpYearCreditCard: Spinner
    private lateinit var mCVCCreditCard: EditText
    private lateinit var mButtonVerify: Button
    private lateinit var mTextViewPrice: TextView

    private lateinit var mTypeChat: String
    private lateinit var mSpecialty: String

    private lateinit var mValidateCreditCardPresenter: IValidateCreditCardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate_credit_card)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Ingrese su tarjeta de credito"

        mHolderCreditCard = findViewById(R.id.edit_text_holder_credit_card)
        mNumberCreditCard = findViewById(R.id.edit_text_number_credit_card)
        mExpMonthCreditCard = findViewById(R.id.spinner_expMonth_credit_card)
        mExpYearCreditCard = findViewById(R.id.spinner_expYear_credit_card)
        mCVCCreditCard = findViewById(R.id.edit_text_cvc_credit_card)
        mButtonVerify = findViewById(R.id.button_verify)
        mTextViewPrice = findViewById(R.id.msg_consultation_price)

        if(intent.extras != null){
            mTypeChat = intent.getStringExtra("chatType")
            mSpecialty = intent.getStringExtra("specialty")
        }

        mValidateCreditCardPresenter = ValidateCreditCardPresenterImpl(this, EstacionVitalRemoteDataSource.INSTANCE, EVTwilioChatRemoteDataSource.instance,
                SharedPrefManager(
                        getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                                Context.MODE_PRIVATE)
                ))

        setMonths()
        setYears()

        mButtonVerify.setOnClickListener {
            if(mHolderCreditCard.text.toString() != "" && mNumberCreditCard.text.toString() != "" && mExpMonthCreditCard.selectedItem.toString() != "" &&
                    mExpYearCreditCard.selectedItem.toString() != "" && mCVCCreditCard.text.toString() != ""){
                val year = mExpYearCreditCard.selectedItem.toString()
                var month: String
                if(mExpMonthCreditCard.selectedItem.toString().length == 1){
                    month = "0${mExpMonthCreditCard.selectedItem}"
                }
                else{
                    month = mExpMonthCreditCard.selectedItem.toString()
                }
                mValidateCreditCardPresenter.validateCreditCard(mHolderCreditCard.text.toString(), year, month,
                        mNumberCreditCard.text.toString(), mCVCCreditCard.text.toString(), mSpecialty, mTypeChat)
            }
            else{
                this.toast("Debe llenar todos los campos para verificar su tarjeta.")
            }
        }
        mTextViewPrice.text = "Precio a debitar: $CONSULTANCE_PRICE"

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

    override fun showProcessingCreditCard() {
        this.showProgressDialog(getString(R.string.msg_processing_credit_card))
    }

    override fun showCreatingRoomLoading() {
        this.showProgressDialog(getString(R.string.loading_examination_creation))
    }

    override fun showErrorMessage() {
        this.toast(getString(R.string.generic_500_error))
    }

    override fun hideLoading() {
        this.hideProgressDialog()
    }

    override fun prepareToNavigateToChat(data: EVChannel) {
        val intentChatWindow = Intent(this, TwilioChatActivity::class.java)
        intentChatWindow.putExtra("chatType", data.type)
        intentChatWindow.putExtra("specialty", data.specialty)
        intentChatWindow.putExtra("room_id",data.unique_name)
        intentChatWindow.putExtra("channel", data)
        startActivity(intentChatWindow)
    }

    override fun showErrorProcessingCreditCard(msg: String) {
        this.toast(msg)
    }

    override fun showExpirationMessage() {
        super.showExpirationMessage()
    }

    fun setMonths(){
        val months: MutableList<String> = ArrayList<String>()
        for(m in 1..12){
            months.add("$m")
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.spinner_item, months)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        mExpMonthCreditCard.adapter = adapter
    }

    fun setYears(){
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years: MutableList<String> = ArrayList<String>()
        for(i in 0..10){
            years.add("${currentYear+i}")
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.spinner_item, years)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        mExpYearCreditCard.adapter = adapter
    }
}
