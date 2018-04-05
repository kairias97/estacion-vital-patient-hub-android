package com.estacionvital.patienthub.ui.activities

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
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.presenter.ITwilioChatPresenter
import com.estacionvital.patienthub.presenter.implementations.TwilioChatPresenterImpl
import com.estacionvital.patienthub.ui.adapters.MessageAdapter
import com.estacionvital.patienthub.ui.views.ITwilioChatView
import com.estacionvital.patienthub.util.CHAT_FREE
import com.estacionvital.patienthub.util.toast
import com.twilio.chat.Channel
import com.twilio.chat.Message

class TwilioChatActivity : BaseActivity(), ITwilioChatView, MessageAdapter.OnMessageSelectedListener {


    /*
    private lateinit var mTypeChat: String
    private lateinit var mSpecialtySelected: String
    private lateinit var mRoomID: String
    private var mIsFinished: Boolean = false*/
    private lateinit var mTwilioChatPresenter: ITwilioChatPresenter
    private lateinit var mRecyclerView: RecyclerView

    //private lateinit var mCurrentChannel: Channel
    private lateinit var messageTxt: EditText
    private lateinit var sendBtn: ImageButton
    private lateinit var sendMessageLbl: TextView
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


        messageTxt = findViewById(R.id.edit_text_message)
        sendBtn = findViewById(R.id.image_button_send)
        sendMessageLbl = findViewById(R.id.label_no_message)

        sendMessageLbl.visibility = View.GONE


        mTwilioChatPresenter = TwilioChatPresenterImpl(this, EVTwilioChatRemoteDataSource.instance)

        //Listeners setup

        sendBtn.setOnClickListener{
            val message = messageTxt.text.toString()
            mTwilioChatPresenter.sendMessage(mEVChannel, message)
            //sendMessage(message)
            messageTxt.setText("")
            //sendBtn.isEnabled = false
        }


        //showMessageLoading()
        mTwilioChatPresenter.setupChatChannel(mEVChannel)
        //mTwilioChatPresenter.retrieveChannel(mRoomID)

    }
    override fun bindMessageTextInputListener() {
        messageTxt.addTextChangedListener(object: TextWatcher{
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
        messageTxt.keyListener = null
    }

    override fun enableMessageTextInput() {
        messageTxt.isEnabled = true
    }

    override fun disableMessageTextInput() {
        messageTxt.isEnabled = false
    }
    override fun enableSendButton() {
        sendBtn.isEnabled = true
    }

    override fun disableSendButton() {
        sendBtn.isEnabled = false
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
        NavUtils.navigateUpFromSameTask(this)
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
            sendMessageLbl.visibility = View.VISIBLE
        }
        else{
            sendMessageLbl.visibility = View.GONE
        }
        //hideLoading()
    }

    override fun addMessageToUI(message: Message) {
        sendMessageLbl.visibility = View.GONE
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
        if(messageTxt.text.toString() != "" || messageTxt.text.toString() != ""){
            sendBtn.isEnabled = true
        }
        else{
            sendBtn.isEnabled = false
        }
    }*/
}
