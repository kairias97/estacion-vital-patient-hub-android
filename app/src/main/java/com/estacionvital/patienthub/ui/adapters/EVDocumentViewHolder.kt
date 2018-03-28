package com.estacionvital.patienthub.ui.adapters

import android.content.res.Resources
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.Document
import com.estacionvital.patienthub.util.DateUtil

/**
 * Created by kevin on 24/3/2018.
 */
class EVDocumentViewHolder : RecyclerView.ViewHolder{
    private val documentTitleTextView: TextView
    private val documentCreatedDateTextView: TextView
    private val documentContainerConstraintLayout: ConstraintLayout
    constructor(itemView: View): super(itemView){
        documentTitleTextView = itemView.findViewById(R.id.text_document_title)
        documentContainerConstraintLayout = itemView.findViewById(R.id.constraint_layout_document_container)
        documentCreatedDateTextView = itemView.findViewById(R.id.text_document_created_date)
    }
    fun bindData(document: Document, listener: EVDocumentAdapter.EVDocumentListener){
        documentTitleTextView.text = "${document.specialty} - ${document.examinationType}"
        Log.i("fechaDocs", document.createdAt.substring(0, 9))
        documentCreatedDateTextView.text = DateUtil.parseDateStringToFormat(document.createdAt.substring(0, 9),
                "yyyy-MM-dd", "dd/MM/yyyy")
        documentContainerConstraintLayout.setOnClickListener {
            listener.onDocumentClickedListener(document)
        }
    }
}