package com.estacionvital.patienthubestacionvital.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.model.Document

/**
 * Created by kevin on 24/3/2018.
 */
class EVDocumentAdapter : RecyclerView.Adapter<EVDocumentViewHolder> {

    private lateinit var mDocuments : MutableList<Document>
    private val mListener: EVDocumentListener

    interface EVDocumentListener {
        fun onDocumentClickedListener(document: Document)
    }

    constructor(documents: MutableList<Document>, listener: EVDocumentListener):super() {
        this.mDocuments = documents
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): EVDocumentViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return EVDocumentViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mDocuments.size
    }

    override fun onBindViewHolder(holder: EVDocumentViewHolder?, position: Int) {
        holder?.bindData(mDocuments[position], this.mListener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_document
    }
    fun setDocuments(documents: MutableList<Document>) {
        this.mDocuments = documents
    }
}