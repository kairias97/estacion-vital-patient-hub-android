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
    private val mNameTextView: TextView
    private val mDateTextView: TextView
    private val mDoctorTextView: TextView
    private val mStatusTextView: TextView


    constructor(itemView: View): super(itemView){
        mNameTextView = itemView.findViewById(R.id.textView_channel_name)
        mDateTextView = itemView.findViewById(R.id.textView_channel_date)
        mDoctorTextView = itemView.findViewById(R.id.textView_channel_doctor)
        mStatusTextView = itemView.findViewById(R.id.textView_channel_status)
    }
    fun bindData(channel: EVChannel, listener: ConversationHistoryAdapter.OnChannelSelectedListener){
        mNameTextView.text = "Chat con ${channel.specialty}"
        if(channel.twilioChannel != null){

            val date = channel.twilioChannel!!.dateCreatedAsDate
            mDateTextView.text = DateUtil.parseDateToFormat(date,"dd/MM/yyyy")
            //mDateTextView.text = channel.twilioChannel!!.dateCreated
            mDoctorTextView.text = if(channel.doctorName.isNullOrEmpty() || channel.doctorName == "false")
                "Pendiente de doctor" else "Dr. ${channel.doctorName}"
            mStatusTextView.text = if (!channel.isFinished) "Abierto" else "Finalizado"
        }
        itemView.setOnClickListener{
            listener.onChannelItemSelected(channel)
        }
    }

}