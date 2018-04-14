package com.estacionvital.patienthub.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.presenter.ITwilioChatPresenter
import com.estacionvital.patienthub.presenter.implementations.TwilioChatPresenterImpl
import com.estacionvital.patienthub.ui.adapters.MessageAdapter
import com.estacionvital.patienthub.ui.views.ITwilioChatView
import com.estacionvital.patienthub.util.toast
import com.twilio.chat.Message

class TwilioChatActivity : BaseActivity(), ITwilioChatView, MessageAdapter.OnMessageSelectedListener {
    override fun showChannelLoadingMessage() {
        showProgressDialog(getString(R.string.loading_channel_message))
    }

    override fun hideChannelLoadingMessage() {
        hideProgressDialog()
    }


    /*
    private lateinit var mTypeChat: String
    private lateinit var mSpecialtySelected: String
    private lateinit var mRoomID: String
    private var mIsFinished: Boolean = false*/
    private lateinit var mTwilioChatPresenter: ITwilioChatPresenter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mFreeChatBannerTextView: TextView

    //private lateinit var mCurrentChannel: Channel
    private lateinit var mMessageEditText: EditText
    private lateinit var mSendBtn: ImageButton
    private lateinit var mSendMessageTextView: TextView
    private lateinit var mEVChannel:EVChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twilio_chat)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.extras != null) {
            /*
            mTypeChat = intent.extras.getString("chatType")
            mSpecialtySelected = intent.extras.getString("specialty")
            mRoomID = intent.extras.getString("room_id")
            if(intent.hasExtra("isFinished")){
                mIsFinished = intent.extras.getBoolean("isFinished")
            }*/
            mEVChannel = intent.extras.getParcelable("channel")

        }
        //Refactorizar para hacer el format desde el R.string
        supportActionBar!!.title = getString(R.string.title_chat_activity).format(mEVChannel.specialty)
        //"Chat con ${mSpecialtySelected}"

        mRecyclerView = findViewById(R.id.recycler_messages)
        mRecyclerView.adapter = MessageAdapter(ArrayList<Message>(),this)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)
        (mRecyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true


        mMessageEditText = findViewById(R.id.edit_text_message)
        mSendBtn = findViewById(R.id.image_button_send)
        mSendMessageTextView = findViewById(R.id.label_no_message)
        mFreeChatBannerTextView = findViewById(R.id.free_chat_warning_text_view)

        mSendMessageTextView.visibility = View.GONE


        mTwilioChatPresenter = TwilioChatPresenterImpl(this, EVTwilioChatRemoteDataSource.instance,
                EstacionVitalRemoteDataSource.INSTANCE)

        //Listeners setup

        mSendBtn.setOnClickListener{
            val message = mMessageEditText.text.toString()
            mTwilioChatPresenter.sendMessage(mEVChannel, message)
            //sendMessage(message)
            mMessageEditText.setText("")
            //mSendBtn.isEnabled = false
        }


        //showMessageLoading()
        mTwilioChatPresenter.setupChatChannel(mEVChannel)
        //mTwilioChatPresenter.retrieveChannel(mRoomID)

    }
    override fun hideMessagingControls() {
        mMessageEditText.visibility = View.GONE
        mSendBtn.visibility  = View.GONE
    }

    override fun showMessagingControls() {
        mMessageEditText.visibility = View.VISIBLE
        mSendBtn.visibility  = View.VISIBLE
    }
    override fun showFreeChatBanner() {
        mFreeChatBannerTextView.visibility = View.VISIBLE
    }
    override fun bindMessageTextInputListener() {
        mMessageEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mTwilioChatPresenter.onMessageTextChanged(s.toString())
                //changeBtnEnabled()
            }
        })
    }

    override fun unbindMessageTextInputListener() {
        mMessageEditText.keyListener = null
    }

    override fun enableMessageTextInput() {
        mMessageEditText.isEnabled = true
    }

    override fun disableMessageTextInput() {
        mMessageEditText.isEnabled = false
    }
    override fun enableSendButton() {
        mSendBtn.isEnabled = true
    }

    override fun disableSendButton() {
        mSendBtn.isEnabled = false
    }
    override fun showErrorSendingMessage() {
        this.toast(R.string.send_message_error)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                returnTop()
            }
        }
        return true
    }

    override fun showFreeChatDisabledMessage() {
        this.toast(R.string.disabled_free_chat_message)
    }
    override fun onBackPressed() {
        returnTop()
    }
    private fun returnTop(){
        if (parent == null) {
            val parentIntent = Intent(this, MainActivityDrawer::class.java)
            parentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(parentIntent)
        } else {
            NavUtils.navigateUpFromSameTask(this)
        }

    }

    override fun showMessageLoading() {
        showProgressDialog(getString(R.string.loading_messages_channel))
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun showErrorLoading() {
        this.toast(getString(R.string.generic_500_error))
    }
    /*
    override fun getChannelFromID(channel: Channel) {
        mCurrentChannel = channel
        //move this to presenter
        mTwilioChatPresenter.retrieveMessages(mCurrentChannel)
        mTwilioChatPresenter.setChannelListener(mCurrentChannel)
    }*/

    override fun setChannelMessagesUI(messages: MutableList<Message>) {
        (mRecyclerView.adapter as? MessageAdapter)!!.setMessageList(messages)
        (mRecyclerView.adapter as? MessageAdapter)!!.notifyDataSetChanged()
        (mRecyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true
        if(messages.count() == 0){
            mSendMessageTextView.visibility = View.VISIBLE
        }
        else{
            mSendMessageTextView.visibility = View.GONE
        }
        //hideLoading()
    }

    override fun addMessageToUI(message: Message) {
        mSendMessageTextView.visibility = View.GONE
        (mRecyclerView.adapter as MessageAdapter).addNewMessage(message)
        (mRecyclerView.adapter as MessageAdapter).notifyDataSetChanged()
        mRecyclerView.scrollToPosition((mRecyclerView.adapter as MessageAdapter).getMessageList().count() - 1)
    }


    override fun onMessageSelected(message: Message) {

    }

    override fun showDoctorLeaved() {
        this.toast(getString(R.string.label_doctor_left))
    }

    override fun showDoctorJoined() {
        this.toast(getString(R.string.label_doctor_joined))
    }
    /*
    private fun changeBtnEnabled(){
        if(mMessageEditText.text.toString() != "" || mMessageEditText.text.toString() != ""){
            mSendBtn.isEnabled = true
        }
        else{
            mSendBtn.isEnabled = false
        }
    }*/
}
