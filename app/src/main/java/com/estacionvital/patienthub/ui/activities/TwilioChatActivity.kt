package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.presenter.ITwilioChatrPresenter
import com.estacionvital.patienthub.presenter.implementations.TwilioChatPresenterImpl
import com.estacionvital.patienthub.ui.adapters.MessageAdapter
import com.estacionvital.patienthub.ui.views.ITwilioChatView
import com.estacionvital.patienthub.util.toast
import com.twilio.chat.Channel
import com.twilio.chat.Message

class TwilioChatActivity : BaseActivity(), ITwilioChatView, MessageAdapter.OnMessageSelectedListener {

    private lateinit var mTypeChat: String
    private lateinit var mSpecialtySelected: String
    private lateinit var mRoomID: String
    private var mIsFinished: Boolean = false
    private lateinit var mTwilioChatPresenter: ITwilioChatrPresenter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessageAdapter: MessageAdapter
    private lateinit var mCurrentChannel: Channel
    private lateinit var messageTxt: EditText
    private lateinit var sendBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twilio_chat)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.extras != null) {
            mTypeChat = intent.extras.getString("chatType")
            mSpecialtySelected = intent.extras.getString("specialty")
            mRoomID = intent.extras.getString("room_id")
            if(intent.hasExtra("isFinished")){
                mIsFinished = intent.extras.getBoolean("isFinished")
            }
        }

        supportActionBar!!.title = "Chat con ${mSpecialtySelected}"

        mRecyclerView = findViewById<RecyclerView>(R.id.recycler_messages)
        messageTxt = findViewById<EditText>(R.id.edit_text_message)
        sendBtn = findViewById<ImageButton>(R.id.image_button_send)

        if(mIsFinished){
            messageTxt.isEnabled = false
            messageTxt.keyListener = null
        }

        sendBtn.setOnClickListener{
            val message = messageTxt.text.toString()
            sendMessage(message)
            messageTxt.setText("")
            sendBtn.isEnabled = false
        }
        messageTxt.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeBtnEnabled()
            }
        })
        sendBtn.isEnabled = false

        mTwilioChatPresenter = TwilioChatPresenterImpl(this, EVTwilioChatRemoteDataSource.instance)
        showMessageLoading()
        mTwilioChatPresenter.retrieveChannel(mRoomID)

        mMessageAdapter = MessageAdapter(ArrayList<Message>(),this)
        mRecyclerView.adapter = mMessageAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        (mRecyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true
        mRecyclerView.setHasFixedSize(true)
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

    override fun showMessageLoading() {
        showProgressDialog(getString(R.string.loading_messages_channel))
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    override fun showErrorLoading() {
        this.toast(getString(R.string.generic_500_error))
    }

    override fun getChannelFromID(channel: Channel) {
        mCurrentChannel = channel
        mTwilioChatPresenter.retrieveMessages(mCurrentChannel)
        mTwilioChatPresenter.setChannelListener(mCurrentChannel)
    }

    override fun getMessagesFromChannel(messages: MutableList<Message>) {
        (mRecyclerView.adapter as? MessageAdapter)!!.setMessageList(messages)
        (mRecyclerView.adapter as? MessageAdapter)!!.notifyDataSetChanged()
        (mRecyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true
        hideLoading()
    }

    override fun getNewMessage(message: Message) {
        (mRecyclerView.adapter as MessageAdapter).addNewMessage(message)
        (mRecyclerView.adapter as MessageAdapter).notifyDataSetChanged()
        mRecyclerView.scrollToPosition((mRecyclerView.adapter as MessageAdapter).getMessageList().count() - 1)
    }

    override fun sendMessage(body: String) {
        mTwilioChatPresenter.sendMessage(mCurrentChannel,body)
    }

    override fun onMessageSelected(message: Message) {

    }
    private fun changeBtnEnabled(){
        if(messageTxt.text.toString() != "" || messageTxt.text.toString() != ""){
            sendBtn.isEnabled = true
        }
        else{
            sendBtn.isEnabled = false
        }
    }
}
