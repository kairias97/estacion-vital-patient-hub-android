package com.estacionvital.patienthubestacionvital.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.data.local.SharedPrefManager
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.Document
import com.estacionvital.patienthubestacionvital.presenter.IDocumentHistoryPresenter
import com.estacionvital.patienthubestacionvital.presenter.implementations.DocumentHistoryPresenterImpl
import com.estacionvital.patienthubestacionvital.ui.activities.BaseActivity
import com.estacionvital.patienthubestacionvital.ui.adapters.EVDocumentAdapter
import com.estacionvital.patienthubestacionvital.ui.fragmentViews.IDocumentHistoryFragmentView

/**
 * Created by kevin on 24/3/2018.
 */
class DocumentHistoryFragment: Fragment(), IDocumentHistoryFragmentView {

    private lateinit var mListener: DocumentHistoryFragmentListener
    private lateinit var mDocumentRecycler: RecyclerView
    private lateinit var mPresenter: IDocumentHistoryPresenter

    override fun showExpirationMessage() {
        (activity as BaseActivity).showExpirationMessage()
    }
    interface DocumentHistoryFragmentListener {
        fun onDocumentLoadingStarted()
        fun onDocumentLoadingFinished()
        fun onDocumentLoadingError()
        fun onDocumentSelected(document: Document)

    }
    companion object {
        fun newInstance(listener: DocumentHistoryFragmentListener): DocumentHistoryFragment {
            val fragment = DocumentHistoryFragment()
            fragment.mListener = listener
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mPresenter = DocumentHistoryPresenterImpl(SharedPrefManager(
                activity.getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        )
                ,EstacionVitalRemoteDataSource.INSTANCE, this)
        this.mPresenter.loadDocuments()
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_document_history, container, false)
        mDocumentRecycler = view.findViewById(R.id.recycler_documents)
        mDocumentRecycler.setHasFixedSize(true)
        mDocumentRecycler.layoutManager = LinearLayoutManager(activity)
        mDocumentRecycler.adapter = EVDocumentAdapter(ArrayList<Document>(),
                object: EVDocumentAdapter.EVDocumentListener {
                    override fun onDocumentClickedListener(document: Document) {
                        mListener?.onDocumentSelected(document)
                    }

                })

        return view
    }
    override fun showDocumentFetchProgress() {
        this.mListener?.onDocumentLoadingStarted()
    }

    override fun onDetach() {
        super.onDetach()
    }
    override fun hideDocumentFetchProgress() {
        this.mListener?.onDocumentLoadingFinished()
    }

    override fun updateDocumentsListUI(documents : MutableList<Document>) {
        (this.mDocumentRecycler.adapter as EVDocumentAdapter).setDocuments(documents)

        (this.mDocumentRecycler.adapter as EVDocumentAdapter).notifyDataSetChanged()
    }

    override fun showError() {
        this.mListener?.onDocumentLoadingError()
    }

}