package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.*
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.ISplashPresenter
import com.estacionvital.patienthub.ui.views.ISplashView
import com.estacionvital.patienthub.util.NETMOBILE_AUTH_CREDENTIAL
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
    //Second function executed on the notification flow
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
    //Third function executed in the flow
    private fun fetchTwilioChannel(evChannel: EVChannel){
        mEVTwilioChatRemoteDataSource.findChannelByID(evChannel.unique_name, object: IEVTwilioFindChannelByIDCallback {
            override fun onSuccess(channel: Channel) {
                evChannel.twilioChannel = channel
                retrieveEVUSerProfileForChat(evChannel)
            }

            override fun onFailure() {
                redirectToMain()
            }

        })
    }
    //First function executed in the notification flow
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


    private fun retrieveEVUSerProfileForChat(evChannel: EVChannel) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mEVRemoteDataSource.retrieveEVUserProfile(token,
                object: IEVRetrieveProfileCallback {
                    override fun onTokenExpired() {
                        redirectToMain()
                    }

                    override fun onSuccess(result: EVRetrieveProfileResponse) {
                        if(result.status == "success"){
                            EVUserSession.instance.userProfile = result.data

                            mSplashView.navigateToChat(evChannel)
                        }
                        else{
                            redirectToMain()
                        }
                    }

                    override fun onFailure() {
                        redirectToMain()
                    }
                })
    }

    private val mSplashTimeOut: Int = 1000
    private val mEVRemoteDataSource: EstacionVitalRemoteDataSource
    private val mNetMobileRemoteDataSource: NetMobileRemoteDataSource
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource
    private val mSplashView: ISplashView
    private val mSharedPrefManager: SharedPrefManager

    constructor(view: ISplashView, sharedPref: SharedPrefManager, evRemoteDS: EstacionVitalRemoteDataSource,
                evTwilioChatRemoteDS: EVTwilioChatRemoteDataSource, netMobileRemoteDataSource: NetMobileRemoteDataSource) {
        this.mSplashView = view
        this.mSharedPrefManager = sharedPref
        this.mEVRemoteDataSource = evRemoteDS
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDS
        this.mNetMobileRemoteDataSource = netMobileRemoteDataSource
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
            validateClubSuscriptions()
        } else {
            redirectToNumberVerification()
        }
        //If it is not logged then go to Registration

    }
    private fun validateClubSuscriptions() {

        mNetMobileRemoteDataSource.retrieveSubscriptionActive(SuscriptionActiveRequest(EVUserSession.instance.phoneNumber,
                NETMOBILE_AUTH_CREDENTIAL), object:ISuscriptionActiveCallback {
            override fun onSuccess(response: List<SuscriptionActiveResponse>) {
                if (response.isNotEmpty()) {
                    mSplashView.navigateToMain()
                } else {
                    mSplashView.navigateToClubSuscription()
                }
            }

            override fun onFailure() {
                //See what we can do on failure
                mSplashView.showSuscriptionValidationError()
                mSplashView.close()
            }

        } )
    }
}