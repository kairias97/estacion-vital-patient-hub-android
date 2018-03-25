package com.estacionvital.patienthubestacionvital.ui.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.data.local.SharedPrefManager
import com.estacionvital.patienthubestacionvital.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.EVSpecialtiesResponse
import com.estacionvital.patienthubestacionvital.presenter.ISpecialtySelectionPresenter
import com.estacionvital.patienthubestacionvital.presenter.implementations.SpecialtySelectionPresenterImpl
import com.estacionvital.patienthubestacionvital.ui.views.ISpecialtySelectionView
import com.estacionvital.patienthubestacionvital.util.CHAT_FREE
import com.estacionvital.patienthubestacionvital.util.CHAT_PREMIUM
import com.estacionvital.patienthubestacionvital.util.toast
import com.twilio.chat.Channel

class SpecialtySelectionActivity : BaseActivity(), ISpecialtySelectionView {

    private lateinit var mSpecialtySelectionPresenter: ISpecialtySelectionPresenter
    private lateinit var mSpinner: Spinner
    private lateinit var mAcceptButton: Button
    private lateinit var mCancelButton: Button
    private lateinit var service_type: String

    private lateinit var mTypeChat: String
    private lateinit var selected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialty_selection)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Seleccione una especialidad"

        mSpinner = findViewById<Spinner>(R.id.spinner_specialties)
        mAcceptButton = findViewById<Button>(R.id.button_selected_specialty)
        mCancelButton = findViewById<Button>(R.id.button_cancel_specialty)

        mSpecialtySelectionPresenter = SpecialtySelectionPresenterImpl(SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ),this, EstacionVitalRemoteDataSource.INSTANCE, EVTwilioChatRemoteDataSource.instance)

        mSpecialtySelectionPresenter.retrieveSpecialtiesChat()

        if (intent.extras != null) {
            mTypeChat = intent.extras.getString("chatType")
        }

        mAcceptButton.setOnClickListener {
            //faltaria verificar el parametro para ver si es chat free o premium, en caso de ser premium mostrar dialogo
            if(mTypeChat == CHAT_FREE){
                selected = mSpinner.selectedItem.toString()
                service_type = "free"
                mSpecialtySelectionPresenter.createNewExamination(selected,service_type)
                //prepareToNavigateToCoupon(selected, service_type)
            }
            else if(mTypeChat == CHAT_PREMIUM){
                //falta agregar verificar disponibilidad, por el momento tendra el mismo comportamiento
                //igualmente, al ser premium, nos faltaria ver como mandarlo al activity de modo de pago
                selected = mSpinner.selectedItem.toString()
                service_type = "paid"
                mSpecialtySelectionPresenter.retrieveDoctorAvailability(selected)

            }
        }
        mCancelButton.setOnClickListener {
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
    private fun returnTop(){
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun onResume() {
        super.onResume()
        mSpecialtySelectionPresenter.retrieveSpecialtiesChat()
    }

    override fun showProgressDialog() {
        this.showProgressDialog(getString(R.string.loading_specialties_msg))
    }

    override fun showAvailabilityProgressDialog() {
        this.showProgressDialog(getString(R.string.loading_doctor_availability))
    }

    override fun showCreatingExaminationProgress() {
        this.showProgressDialog(getString(R.string.loading_examination_creation))
    }

    override fun showErrorLoading() {
        this.toast(getString(R.string.generic_500_error))
    }

    override fun hideLoading() {
        this.hideProgressDialog()
    }
    override fun setSpecialtiesData(data: EVSpecialtiesResponse) {
        //settear el recycler
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.spinner_item, data.specialtes)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        mSpinner.adapter = adapter
    }

    override fun getDoctorAvailability(data: Boolean) {
        if(data == false){
            this.showConfirmDialog(titleResId = R.string.title_confirmation_doctor_available,
                    iconResId = R.drawable.ic_error_black_24dp,
                    messageResId = R.string.message_no_doctor_available,
                    positiveBtnResId = R.string.dialog_yes_no_doctor_available,
                    negativeBtnResId = R.string.dialog_cancel,
                    positiveListener = object:DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                            //mSpecialtySelectionPresenter.createNewExamination(selected,service_type)
                            paymentMethodDialog()
                        }
                    },
                    negativeListener = object:DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                        }
                    })
        }
        else{
            //mSpecialtySelectionPresenter.createNewExamination(selected, service_type)
            paymentMethodDialog()
        }
        hideLoading()
    }

    override fun getCreatedRoomID(data: String) {
        mSpecialtySelectionPresenter.joinEVTwilioRoom(data)
    }

    override fun prepareToNavigateToChat(data: Channel) {
        navigateToChatWindow(selected, data.uniqueName)
    }

    override fun prepareToNavigateToCoupon(specialty: String, typeChat: String) {
        val intentTarget = Intent(this, ValidateCouponActivity::class.java)
        intentTarget.putExtra("chatType", typeChat)
        intentTarget.putExtra("specialty", specialty)
        startActivity(intentTarget)
    }

    override fun prepareToNavigateToCreditCard(specialty: String, typeChat: String) {
        val intentTarget = Intent(this, ValidateCreditCardActivity::class.java)
        startActivity(intentTarget)
    }
    private fun paymentMethodDialog(){
        this.showConfirmDialog(titleResId = R.string.title_payment_method,
                iconResId = R.drawable.ic_error_black_24dp,
                messageResId = R.string.message_payment_method,
                positiveBtnResId = R.string.dialog_coupon,
                negativeBtnResId = R.string.dialog_credit_card,
                positiveListener = object:DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog!!.dismiss()
                        prepareToNavigateToCoupon(selected, service_type)
                    }
                },
                negativeListener = object:DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog!!.dismiss()
                        prepareToNavigateToCreditCard(selected, service_type)
                    }
                })
    }
    private fun navigateToChatWindow(selected: String, room_id: String){
        val intentChatWindow = Intent(this, TwilioChatActivity::class.java)
        intentChatWindow.putExtra("chatType", mTypeChat)
        intentChatWindow.putExtra("specialty", selected)
        intentChatWindow.putExtra("room_id",room_id)
        startActivity(intentChatWindow)
    }
}
