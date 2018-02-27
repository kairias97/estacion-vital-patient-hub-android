package com.estacionvital.patienthub.ui.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.EVClub

/**
 * Created by kevin on 26/2/2018.
 */
public class EVClubAdapter: RecyclerView.Adapter<EVClubViewHolder> {
    private lateinit var clubs: List<EVClub>

    constructor(myDataSet: List<EVClub>){
        this.clubs = myDataSet
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): EVClubViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return EVClubViewHolder(view)
    }

    override fun getItemCount(): Int {
        return clubs.count()
    }

    override fun onBindViewHolder(holder: EVClubViewHolder?, position: Int) {
        (holder as EVClubViewHolder).bindData(clubs.get(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_checkbox_suscription
    }

    public fun setClubsList(list: List<EVClub>){
        this.clubs = list
    }
}