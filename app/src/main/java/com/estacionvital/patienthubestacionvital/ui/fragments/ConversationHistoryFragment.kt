package com.estacionvital.patienthubestacionvital.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.*
import com.estacionvital.patienthubestacionvital.presenter.IConversationHistoryPresenter
import com.estacionvital.patienthubestacionvital.presenter.implementations.ConversationHistoryPresenterImpl
import com.estacionvital.patienthubestacionvital.ui.activities.TwilioChatActivity
import com.estacionvital.patienthubestacionvital.ui.adapters.ConversationHistoryAdapter
import com.estacionvital.patienthubestacionvital.ui.fragmentViews.IConversationHistoryFragmentView
import com.estacionvital.patienthubestacionvital.util.CHAT_FREE
import com.estacionvital.patienthubestacionvital.util.CHAT_PREMIUM
import kotlinx.android.synthetic.main.app_bar_main_activity_drawer.*
import com.google.android.gms.cast.CastRemoteDisplayLocalService.startService
import com.estacionvital.patienthubestacionvital.services.RegistrationIntentService



/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConversationHistoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConversationHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConversationHistoryFragment : Fragment(), IConversationHistoryFragmentView, ConversationHistoryAdapter.OnChannelSelectedListener {

    private var mParam1: String? = null

    private var mListener: OnConversationHistorytInteraction? = null

    private lateinit var mFabButton: FloatingActionButton
    private lateinit var mTextViewNoRegister: TextView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mConversationhistoryPresenter: IConversationHistoryPresenter

    private lateinit var mConversationHistoryAdapter: ConversationHistoryAdapter


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
        mTextViewNoRegister = view.findViewById<TextView>(R.id.textView_register_none)
        mTextViewNoRegister.visibility = View.GONE
        mRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        mConversationhistoryPresenter = ConversationHistoryPresenterImpl(this, EstacionVitalRemoteDataSource.INSTANCE, EVTwilioChatRemoteDataSource.instance)
        mConversationhistoryPresenter.retrieveConversationHistory(activity.applicationContext)

        mConversationHistoryAdapter = ConversationHistoryAdapter(ArrayList<EVChannel>(),this)
        mRecyclerView.adapter = mConversationHistoryAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.setHasFixedSize(true)

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

    override fun onResume() {
        super.onResume()
        mConversationhistoryPresenter.retrieveConversationHistory(activity.applicationContext)
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

    override fun setHistory(data: EVUserExaminationData) {
        EVUserSession.instance.twilioToken = data.twilio_token
        mConversationhistoryPresenter.setUpTwilioClient(activity.applicationContext,data.examinations)
    }
    override fun setChannelList(data: MutableList<EVChannel>) {
        var list: MutableList<EVChannel> = ArrayList<EVChannel>()
        if(mParam1 == CHAT_FREE){
            for(channel in data.reversed()){
                if(channel.type=="free"){
                    list.add(channel)
                    break
                }
            }
            if(list.count()>0){
                (mRecyclerView.adapter as? ConversationHistoryAdapter)!!.setChannelsList(list)
                (mRecyclerView.adapter as? ConversationHistoryAdapter)!!.notifyDataSetChanged()
                mTextViewNoRegister.visibility = View.GONE
            }
            else{
                mTextViewNoRegister.visibility = View.VISIBLE
            }
        }
        else if(mParam1 == CHAT_PREMIUM){
            for(channel in data.reversed()){
                if(channel.type=="paid"){
                    list.add(channel)
                }
            }
            if(list.count()>0){
                (mRecyclerView.adapter as? ConversationHistoryAdapter)!!.setChannelsList(list)
                (mRecyclerView.adapter as? ConversationHistoryAdapter)!!.notifyDataSetChanged()
                mTextViewNoRegister.visibility = View.GONE
            }
            else{
                mTextViewNoRegister.visibility = View.VISIBLE
            }
        }
        hideLoading()
        val intent = Intent(activity.applicationContext, RegistrationIntentService::class.java)
        activity.startService(intent)
    }
    //del adapter
    override fun onChannelItemSelected(channel: EVChannel) {
        val targetIntent: Intent = Intent(activity.applicationContext, TwilioChatActivity::class.java)
        targetIntent.putExtra("chatType",mParam1)
        targetIntent.putExtra("room_id", channel.unique_name)
        targetIntent.putExtra("specialty", channel.specialty)
        targetIntent.putExtra("isFinished", channel.status)
        startActivity(targetIntent)
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