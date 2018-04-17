package com.estacionvital.patienthub.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.util.DateUtil
import com.squareup.picasso.Picasso
import com.twilio.chat.Message
import java.util.*

/**
 * Created by dusti on 21/03/2018.
 */
class MessageViewHolder: RecyclerView.ViewHolder {
    private var mTextViewMessage: TextView
    private var mTextViewDate: TextView
    private var mProfileView: ImageView
    private var mTextViewAuthor: TextView

    constructor(itemView: View): super(itemView){
        mTextViewMessage = itemView.findViewById<TextView>(R.id.textView_message_content)
        mTextViewAuthor = itemView.findViewById<TextView>(R.id.textView_message_author)
        mTextViewDate = itemView.findViewById<TextView>(R.id.textView_message_date)
        mProfileView = itemView.findViewById<ImageView>(R.id.profile_view)
    }

    fun bindData(message: Message, listener: MessageAdapter.OnMessageSelectedListener){
        mTextViewMessage.text = message.messageBody
        mTextViewAuthor.text = message.author
        //val valueDate = message.timeStampAsDate
        val date = message.timeStampAsDate

        //mTextViewDate.text = DateUtil.parseDateStringToFormat(date,"yyyy-MM-dd'T'HH:mm:ss","HH:mm dd/MM/yyyy")
        mTextViewDate.text = DateUtil.parseDateToFormat(date, "HH:mm dd/MM/yyyy")
        itemView.setOnClickListener {
            listener.onMessageSelected(message)
        }
        //Be careful with that because it has side effects when it is not setup
        if(message.author == "${EVUserSession.instance.userProfile.name} ${EVUserSession.instance.userProfile.last_name}"){
            Picasso.get()
                    .load(R.mipmap.ic_patient_new_round)
                    .into(mProfileView)
        }
    }
}