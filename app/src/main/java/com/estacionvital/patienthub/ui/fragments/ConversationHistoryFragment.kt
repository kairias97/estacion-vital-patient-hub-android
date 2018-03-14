package com.estacionvital.patienthub.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.ui.fragmentViews.IConversationHistoryFragmentView
import kotlinx.android.synthetic.main.app_bar_main_activity_drawer.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConversationHistoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConversationHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConversationHistoryFragment : Fragment(), IConversationHistoryFragmentView {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnConversationHistorytInteraction? = null

    private lateinit var mFabButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity.fab.visibility = View.GONE
        val view = inflater!!.inflate(R.layout.fragment_conversation_history, container, false)

        mFabButton = view.findViewById<FloatingActionButton>(R.id.fab_chat_add)
        mFabButton.setOnClickListener {
            navigateToSpecialty()
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity.title = "Chat Gratis"
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnConversationHistorytInteraction {
        fun onLoadingHistory()
        fun onHistoryLoadingFinished()
        fun navigateToSpecialty()
    }

    override fun navigateToSpecialty() {
        this.mListener?.navigateToSpecialty()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        fun newInstance(param1: String, listener: OnConversationHistorytInteraction): ConversationHistoryFragment {
            val fragment = ConversationHistoryFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            fragment.mListener = listener
            return fragment
        }
    }
}// Required empty public constructor
