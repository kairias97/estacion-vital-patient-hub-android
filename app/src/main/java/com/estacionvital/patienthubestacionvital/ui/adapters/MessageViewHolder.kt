package com.estacionvital.patienthubestacionvital.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.util.DateUtil
import com.twilio.chat.Message

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

    fun bindData(viewModel: Message, listener: MessageAdapter.OnMessageSelectedListener){
        mTextViewMessage.text = viewModel.messageBody
        mTextViewAuthor.text = viewModel.author
        val date = viewModel.timeStamp.substring(0, viewModel.timeStamp.length-4)
        mTextViewDate.text = DateUtil.parseDateStringToFormat(date,"yyyy-MM-dd'T'HH:mm:ss"," HH:mm dd/MM/yyyy")

        itemView.setOnClickListener {
            listener.onMessageSelected(viewModel)
        }
    }
}