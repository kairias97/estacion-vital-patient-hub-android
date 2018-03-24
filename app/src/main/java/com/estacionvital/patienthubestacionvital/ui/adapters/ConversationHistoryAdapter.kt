package com.estacionvital.patienthubestacionvital.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.model.EVChannel

/**
 * Created by dusti on 18/03/2018.
 */
class ConversationHistoryAdapter: RecyclerView.Adapter<ConversationHistoryViewHolder> {

    private var Channels: MutableList<EVChannel>
    private var listener: OnChannelSelectedListener

    interface OnChannelSelectedListener{
        fun onChannelItemSelected(channel: EVChannel)
    }
    constructor(channels: MutableList<EVChannel>, listener: OnChannelSelectedListener ){
        this.Channels = channels
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ConversationHistoryViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return ConversationHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Channels.count()
    }

    override fun onBindViewHolder(holder: ConversationHistoryViewHolder?, position: Int) {
        (holder as ConversationHistoryViewHolder).bindData(Channels[position],this.listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_channel_history
    }

    fun setChannelsList(list: MutableList<EVChannel>){
        this.Channels = list
    }
}