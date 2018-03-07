package com.estacionvital.patienthub.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.EVClub

/**
 * Created by kevin on 26/2/2018.
 */
class EVClubAdapter: RecyclerView.Adapter<EVClubViewHolder> {
    private var clubs: MutableList<EVClub>
    private  var listener: OnClubSelectedListener

    interface OnClubSelectedListener{
        fun onClubItemClicked(club: EVClub)
    }
    constructor(myDataSet: MutableList<EVClub>, listener: OnClubSelectedListener){
        this.clubs = myDataSet
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): EVClubViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return EVClubViewHolder(view)
    }

    override fun getItemCount(): Int {
        return clubs.count()
    }

    override fun onBindViewHolder(holder: EVClubViewHolder?, position: Int) {
        (holder as EVClubViewHolder).bindData(clubs[position], this.listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_checkbox_suscription
    }

    fun setClubsList(list: MutableList<EVClub>){
        this.clubs = list
    }
    fun getSelectedClubsCount(): Int {
        return this.clubs.count { it.isSelected  || it.isRemoteRegistered}
    }
    fun getSelectedClubs(): List<EVClub>{
        return this.clubs.filter { it.isSelected || it.isRemoteRegistered}

    }

    fun setClub(club: EVClub){
        val clubIndex = this.clubs.indexOf(club)
        if (clubIndex == -1) {
            this.clubs.add(club)
        } else {
            this.clubs[clubIndex] = club
        }

    }
}