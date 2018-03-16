package com.estacionvital.patienthub.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVChatSession
import com.estacionvital.patienthub.model.EVRetrieveUserExaminationResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.IConversationHistoryPresenter
import com.estacionvital.patienthub.presenter.implementations.ConversationHistoryPresenterImpl
import com.estacionvital.patienthub.ui.fragmentViews.IConversationHistoryFragmentView
import com.estacionvital.patienthub.util.CHAT_FREE
import com.estacionvital.patienthub.util.CHAT_PREMIUM
import com.estacionvital.patienthub.util.toast
import com.twilio.chat.*
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
        setTwilioClient()
    }

    private fun setTwilioClient(){
        val props: ChatClient.Properties = ChatClient.Properties.Builder().createProperties()
        ChatClient.create(activity.applicationContext,EVUserSession.instance.twilioToken,
                props, object: CallbackListener<ChatClient>(){
            override fun onSuccess(p0: ChatClient?) {
                EVChatSession.instance.chatClient = p0!!
                EVChatSession.instance.chatClient.setListener(object: ChatClientListener{
                    override fun onChannelDeleted(p0: Channel?) {

                    }

                    override fun onClientSynchronization(p0: ChatClient.SynchronizationStatus?) {
                        //aca
                        if(p0!! == ChatClient.SynchronizationStatus.COMPLETED){
                            /*val channels: List<Channel> = EVChatSession.instance.chatClient.channels.subscribedChannels
                            for (channel in channels) {
                                Log.d(TAG, "Channel named: " + channel.friendlyName)
                            }*/
                            EVChatSession.instance.chatClient.channels.getPublicChannelsList(object: CallbackListener<Paginator<ChannelDescriptor>>(){
                                override fun onSuccess(p0: Paginator<ChannelDescriptor>?) {
                                    for(channel: ChannelDescriptor in p0!!.items){
                                        Log.d(TAG, "Channel named: " + channel.friendlyName);
                                    }
                                }

                                override fun onError(errorInfo: ErrorInfo?) {
                                    super.onError(errorInfo)
                                }
                            })
                        }
                    }

                    override fun onNotificationSubscribed() {

                    }

                    override fun onUserSubscribed(p0: User?) {

                    }

                    override fun onChannelUpdated(p0: Channel?, p1: Channel.UpdateReason?) {

                    }

                    override fun onNotificationFailed(p0: ErrorInfo?) {

                    }

                    override fun onChannelJoined(p0: Channel?) {

                    }

                    override fun onChannelAdded(p0: Channel?) {

                    }

                    override fun onChannelSynchronizationChange(p0: Channel?) {

                    }

                    override fun onNotification(p0: String?, p1: String?) {

                    }

                    override fun onUserUnsubscribed(p0: User?) {

                    }

                    override fun onChannelInvited(p0: Channel?) {

                    }

                    override fun onConnectionStateChange(p0: ChatClient.ConnectionState?) {

                    }

                    override fun onError(p0: ErrorInfo?) {

                    }

                    override fun onUserUpdated(p0: User?, p1: User.UpdateReason?) {

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
