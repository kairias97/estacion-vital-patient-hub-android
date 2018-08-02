package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.IEVCreateNewExaminationCallBack
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioFindChannelByIDCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioJoinChannelCallBack
import com.estacionvital.patienthub.data.remote.Callbacks.IEVValidateCouponCallBack
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.model.EVCreateNewExaminationResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.model.EVValidateCouponResponse
import com.estacionvital.patienthub.presenter.IValidateCouponPresenter
import com.estacionvital.patienthub.ui.views.IValidateCouponView
import com.estacionvital.patienthub.util.PAYMENT_TYPE_COUPON
import com.twilio.chat.Channel

/**
 * Created by dusti on 22/03/2018.
 */
/**
 * Clase presenter para validacion de cupon para chat
 */

class ValidateCouponPresenterImpl: IValidateCouponPresenter {
    override fun expireSession() {
        mSharedPrefManager.clearPreferences()
        mValidateCouponView.showExpirationMessage()
    }

    private val mValidateCouponView: IValidateCouponView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(sharedPrefManager: SharedPrefManager,validateCouponView: IValidateCouponView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource, evTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource){
        this.mValidateCouponView = validateCouponView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }
    //validar cupon
    override fun validateCoupon(coupon: String, specialty: String, serviceType: String) {
        mValidateCouponView.showValidateLoading()
        val token = "Token token=${EVUserSession.instance.authToken}"
        mEstacionVitalRemoteDataSource.validateCoupon(token, coupon, object: IEVValidateCouponCallBack{
            override fun onTokenExpired() {
                mValidateCouponView.hideLoading()
                expireSession()
            }

            override fun onSuccess(response: EVValidateCouponResponse) {
                mValidateCouponView.hideLoading()
                if (response.valid) {
                    createNewExamination(specialty, serviceType, PAYMENT_TYPE_COUPON, coupon, "")
                }else {
                    mValidateCouponView.showInvalidCouponMessage()
                }

            }

            override fun onFailure() {
                mValidateCouponView.hideLoading()
                mValidateCouponView.showErrorLoading()
            }
        })
    }
    //crear una nueva examinacion de chat
    override fun createNewExamination(specialty: String, serviceType: String, type: String, code: String, order_id: String) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mValidateCouponView.showCreatingRoomLoading()
        mEstacionVitalRemoteDataSource.createNewExamination(token, specialty, serviceType, type, code, order_id, object: IEVCreateNewExaminationCallBack {
            override fun onChatCreationDenied() {
                mValidateCouponView.hideLoading()
                //Ignore here since it only applies to free chat
            }

            override fun onTokenExpired() {
                mValidateCouponView.hideLoading()
                expireSession()
            }

            override fun onSuccess(response: EVCreateNewExaminationResponse) {
                //mValidateCouponView.hideLoading()
                val evChannel = EVChannel()
                evChannel.unique_name = response.room
                evChannel.specialty = specialty
                evChannel.isFinished = false
                evChannel.type = serviceType
                joinEVTwilioRoom(evChannel)
            }

            override fun onFailure() {
                mValidateCouponView.hideLoading()
                mValidateCouponView.showErrorLoading()
            }
        })
    }
    //Unir al usuario a un twilio room
    override fun joinEVTwilioRoom(evChannel: EVChannel) {
        mEVTwilioChatRemoteDataSource.findChannelByID(evChannel.unique_name, object: IEVTwilioFindChannelByIDCallback{
            override fun onSuccess(channel: Channel) {
                mEVTwilioChatRemoteDataSource.joinChannel(channel,object: IEVTwilioJoinChannelCallBack {
                    override fun onSuccess() {
                        mValidateCouponView.hideLoading()
                        evChannel.twilioChannel = channel
                        mValidateCouponView.prepareToNavigateToChat(evChannel)
                    }

                    override fun onFailure() {
                        mValidateCouponView.hideLoading()
                        mValidateCouponView.showErrorLoading()
                    }
                })
            }

            override fun onFailure() {
                mValidateCouponView.hideLoading()
                mValidateCouponView.showErrorLoading()
            }
        })
    }
}