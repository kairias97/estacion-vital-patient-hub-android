package com.estacionvital.patienthub.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthub.R
import com.twilio.chat.Message

/**
 * Created by dusti on 21/03/2018.
 */
class MessageAdapter: RecyclerView.Adapter<MessageViewHolder> {
    private var messages: MutableList<Message>
    private var listener: OnMessageSelectedListener

    interface OnMessageSelectedListener{
        fun onMessageSelected(message: Message)
        fun getDoctorName(): String
    }

    constructor(messages: MutableList<Message>, listener: OnMessageSelectedListener){
        this.messages = messages
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MessageViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(viewType, parent,false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: MessageViewHolder?, position: Int) {
        (holder as MessageViewHolder).bindData(messages[position],this.listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_message_chat
    }

    fun setMessageList(list: MutableList<Message>){
        this.messages = list
    }
    fun addNewMessage(message: Message){
        this.messages.add(message)
    }
    fun getMessageList(): MutableList<Message>{
        return this.messages
    }
}