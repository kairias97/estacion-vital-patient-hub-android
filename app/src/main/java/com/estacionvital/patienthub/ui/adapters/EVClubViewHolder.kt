package com.estacionvital.patienthub.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.EVClub

/**
 * Created by kevin on 26/2/2018.
 */
public class EVClubViewHolder: RecyclerView.ViewHolder {
    private lateinit var textView: TextView
    private lateinit var checkBox: CheckBox

    constructor(itemView: View) : super(itemView) {
        checkBox = itemView.findViewById(R.id.checkBox_club_suscribed)
        textView = itemView.findViewById(R.id.text_club_name)

    }
    fun bindData(viewModel: EVClub, listener: EVClubAdapter.OnClubSelectedListener){
        if(viewModel.isRemoteRegistered){
            checkBox.isEnabled = false
            checkBox.isChecked = true
        }
        else{
            checkBox.isChecked = viewModel.isSelected
            checkBox.isEnabled = true
        }
        textView.setText(viewModel.name)
        //To handle the onclick as a whole
        itemView.setOnClickListener{
            listener.onClubItemClicked(viewModel)
        }
        checkBox.setOnClickListener{
            listener.onClubItemClicked(viewModel)
        }

    }
}