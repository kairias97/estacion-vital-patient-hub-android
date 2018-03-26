package com.estacionvital.patienthub.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.util.DateUtil

/**
 * Created by dusti on 18/03/2018.
 */
class ConversationHistoryViewHolder: RecyclerView.ViewHolder {
    private var mTextViewName: TextView
    private var mTextViewDate: TextView
    constructor(itemView: View): super(itemView){
        mTextViewName = itemView.findViewById<TextView>(R.id.textView_channel_name)
        mTextViewDate = itemView.findViewById<TextView>(R.id.textView_channel_date)
    }
    fun bindData(viewModel: EVChannel, listener: ConversationHistoryAdapter.OnChannelSelectedListener){
        mTextViewName.text = "Chat con ${viewModel.specialty}"
        if(viewModel.twilioChannel != null){
            val date = viewModel.twilioChannel!!.dateCreated.substring(0,viewModel.twilioChannel!!.dateCreated.length-4)
            mTextViewDate.text = DateUtil.parseDateStringToFormat(date,"yyyy-MM-dd'T'HH:mm:ss","dd/MM/yyyy")
            //mTextViewDate.text = viewModel.twilioChannel!!.dateCreated
        }
        itemView.setOnClickListener{
            listener.onChannelItemSelected(viewModel)
        }
    }

}