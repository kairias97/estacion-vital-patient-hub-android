package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioClientCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioFindChannelByIDCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IGetChannelByUniqueName
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.model.EVGetChannelByIDRequest
import com.estacionvital.patienthub.model.EVUserExaminationByIDResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.ISplashPresenter
import com.estacionvital.patienthub.ui.views.ISplashView
import com.estacionvital.patienthub.util.TimedTaskHandler
import com.twilio.chat.Channel

/**
 * Created by kevin on 22/2/2018.
 */
class SplashPresenterImpl: ISplashPresenter {
    override fun onChatNotificatioReceived(channelID: String) {
        //Implement here logic to check session on splash screen
        if ( isSessionSaved()) {
            setupSessionInstance()
            fetchNotificationExamination(channelID)
        } else {
            val timedTask = TimedTaskHandler()
            timedTask.executeTimedTask({mSplashView.navigateToNumberVerification()}, mSplashTimeOut.toLong())
        }
    }
    private fun prepareTwilioClient(twilioToken:String, evChannel: EVChannel){
        mEVTwilioChatRemoteDataSource.setupTwilioClient(twilioToken,
                mSplashView.getContext(),
                object: IEVTwilioClientCallback {
            override fun onSuccess() {
                fetchTwilioChannel(evChannel)
            }

            override fun onFailure() {
                redirectToMain()
            }
        })
    }

    private fun fetchTwilioChannel(evChannel: EVChannel){
        mEVTwilioChatRemoteDataSource.findChannelByID(evChannel.unique_name, object: IEVTwilioFindChannelByIDCallback {
            override fun onSuccess(channel: Channel) {
                evChannel.twilioChannel = channel
                mSplashView.navigateToChat(evChannel)
            }

            override fun onFailure() {
                redirectToMain()
            }

        })
    }
    private fun fetchNotificationExamination(channelID:String){
        val authToken = "Token token=${EVUserSession.instance.authToken}"
        mEVRemoteDataSource.getChannelByID(authToken, EVGetChannelByIDRequest(channelID), object:IGetChannelByUniqueName {
            override fun onSuccess(response: EVUserExaminationByIDResponse) {
                val evChannel = EVChannel()
                evChannel.type = response.data.serviceType
                evChannel.specialty = response.data.specialty
                evChannel.isFinished = response.data.finished
                evChannel.unique_name = response.data.channel_name
                evChannel.doctorName = response.data.doctorName

                EVUserSession.instance.twilioToken = response.data.twilioToken
                prepareTwilioClient(EVUserSession.instance.twilioToken, evChannel)

            }

            override fun onFailure() {
                //Meanwhile go to main meanwhile
                redirectToMain()
            }

            override fun onTokenExpired() {
                //Meanwhile go to main meanwhile
                redirectToMain()
            }

        })
    }

    private val mSplashTimeOut: Int = 1000
    private val mEVRemoteDataSource: EstacionVitalRemoteDataSource
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource
    private val mSplashView: ISplashView
    private val mSharedPrefManager: SharedPrefManager

    constructor(view: ISplashView, sharedPref: SharedPrefManager, evRemoteDS: EstacionVitalRemoteDataSource,
                evTwilioChatRemoteDS: EVTwilioChatRemoteDataSource) {
        this.mSplashView = view
        this.mSharedPrefManager = sharedPref
        this.mEVRemoteDataSource = evRemoteDS
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDS
    }
    private fun isSessionSaved(): Boolean{
        val token = mSharedPrefManager.getSharedPrefString(SharedPrefManager.PreferenceKeys.AUTH_TOKEN)
        return token != ""
    }
    private fun setupSessionInstance() {
        val token = mSharedPrefManager.getSharedPrefString(SharedPrefManager.PreferenceKeys.AUTH_TOKEN)
        val phoneNumber = mSharedPrefManager.getSharedPrefString(SharedPrefManager.PreferenceKeys.PHONE_NUMBER)
        EVUserSession.instance.authToken = token
        EVUserSession.instance.phoneNumber = phoneNumber
    }
    private fun redirectToMain() {
        val timedTask = TimedTaskHandler()
        timedTask.executeTimedTask({mSplashView.navigateToMain()}, mSplashTimeOut.toLong())
    }
    private fun redirectToNumberVerification() {
        val timedTask = TimedTaskHandler()
        timedTask.executeTimedTask({mSplashView.navigateToNumberVerification()}, mSplashTimeOut.toLong())
    }
    override fun checkSession() {
        //Implement here logic to check session on splash screen


        if ( isSessionSaved()) {
            setupSessionInstance()
            redirectToMain()
        } else {
            redirectToNumberVerification()
        }
        //If it is not logged then go to Registration

    }
}