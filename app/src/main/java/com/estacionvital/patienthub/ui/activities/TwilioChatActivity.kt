package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.presenter.ITwilioChatrPresenter
import com.estacionvital.patienthub.presenter.implementations.TwilioChatPresenterImpl
import com.estacionvital.patienthub.ui.views.ITwilioChatView
import com.estacionvital.patienthub.util.toast
import com.twilio.chat.Channel
import com.twilio.chat.Message

class TwilioChatActivity : BaseActivity(), ITwilioChatView {

    private lateinit var mTypeChat: String
    private lateinit var mSpecialtySelected: String
    private lateinit var mRoomID: String
    private lateinit var mTwilioChatPresenter: ITwilioChatrPresenter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twilio_chat)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.extras != null) {
            mTypeChat = intent.extras.getString("chatType")
            mSpecialtySelected = intent.extras.getString("specialty")
            mRoomID = intent.extras.getString("room_id")
        }

        supportActionBar!!.title = "Chat con ${mSpecialtySelected}"

        mRecyclerView = findViewById<RecyclerView>(R.id.recycler_messages)

        mTwilioChatPresenter = TwilioChatPresenterImpl(this, EVTwilioChatRemoteDataSource.instance)
        showMessageLoading()
        mTwilioChatPresenter.retrieveChannel(mRoomID)
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
        mTwilioChatPresenter.retrieveMessages(channel)
    }

    override fun getMessagesFromChannel(messages: MutableList<Message>) {
        hideLoading()
        this.toast("Se traen los mensajes")
    }
}
