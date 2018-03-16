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
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVRetrieveUserExaminationResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.IConversationHistoryPresenter
import com.estacionvital.patienthub.presenter.implementations.ConversationHistoryPresenterImpl
import com.estacionvital.patienthub.ui.fragmentViews.IConversationHistoryFragmentView
import com.estacionvital.patienthub.util.CHAT_FREE
import com.estacionvital.patienthub.util.CHAT_PREMIUM
import com.twilio.chat.CallbackListener
import com.twilio.chat.ChatClient
import com.twilio.chat.ChatClientListener
import com.twilio.chat.ErrorInfo
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

    private var mParam1: String? = null

    private var mListener: OnConversationHistorytInteraction? = null

    private lateinit var mFabButton: FloatingActionButton
    private lateinit var mConversationhistoryPresenter: IConversationHistoryPresenter

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

        mConversationhistoryPresenter = ConversationHistoryPresenterImpl(this, EstacionVitalRemoteDataSource.INSTANCE)
        mConversationhistoryPresenter.retrieveConversationHistory()
        //asingacion del titulo en base al parametro pasado
        if(mParam1 != null){
            if(mParam1 == CHAT_FREE){
                activity.title = "Chat Gratis"
            }
            else if(mParam1 == CHAT_PREMIUM){
                activity.title = "Chat Premium"
            }
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnConversationHistorytInteraction {
        fun onLoadingHistory()
        fun onHistoryLoadingFinished()
        fun onHistoryLoadingError()
        fun navigateToSpecialty()
    }

    override fun navigateToSpecialty() {
        this.mListener?.navigateToSpecialty()
    }

    override fun showLoadingProgress() {
        this.mListener?.onLoadingHistory()
    }

    override fun hideLoading() {
        this.mListener?.onHistoryLoadingFinished()
    }

    override fun showError() {
        this.mListener?.onHistoryLoadingError()
    }

    override fun setHistory(data: EVRetrieveUserExaminationResponse) {
        EVUserSession.instance.twilioToken = data.data.twilio_token
    }

    private fun setTwilioClient(){
        val props: ChatClient.Properties = ChatClient.Properties.Builder().createProperties()
        ChatClient.create(activity.applicationContext,EVUserSession.instance.twilioToken,
                props, object: CallbackListener<ChatClient>(){
            override fun onSuccess(p0: ChatClient?) {
                p0!!.setListener(object: ChatClientListener{
                    override fun onClientSynchronization(p0: ChatClient.SynchronizationStatus?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            }

            override fun onError(errorInfo: ErrorInfo?) {
                super.onError(errorInfo)
            }
        })
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
