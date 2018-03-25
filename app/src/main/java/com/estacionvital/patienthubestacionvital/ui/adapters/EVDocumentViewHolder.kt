package com.estacionvital.patienthubestacionvital.ui.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.model.Document

/**
 * Created by kevin on 24/3/2018.
 */
class EVDocumentViewHolder : RecyclerView.ViewHolder{
    private val documentTitleTextView: TextView
    private val documentContainerConstraintLayout: ConstraintLayout
    constructor(itemView: View): super(itemView){
        documentTitleTextView = itemView.findViewById(R.id.text_document_title)
        documentContainerConstraintLayout = itemView.findViewById(R.id.constraint_layout_document_container)
    }
    fun bindData(document: Document, listener: EVDocumentAdapter.EVDocumentListener){
        documentTitleTextView.setText("${document.specialty} - ${document.examinationType} ${document.serviceType}")
        documentContainerConstraintLayout.setOnClickListener {
            listener.onDocumentClickedListener(document)
        }
    }
}