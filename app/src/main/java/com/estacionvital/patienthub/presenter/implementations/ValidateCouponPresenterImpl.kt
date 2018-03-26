package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.IEVCreateNewExaminationCallBack
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioFindChannelByIDCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioJoinChannelCallBack
import com.estacionvital.patienthub.data.remote.Callbacks.IEVValidateCouponCallBack
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVCreateNewExaminationResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.model.EVValidateCouponResponse
import com.estacionvital.patienthub.presenter.IValidateCouponPresenter
import com.estacionvital.patienthub.ui.views.IValidateCouponView
import com.twilio.chat.Channel

/**
 * Created by dusti on 22/03/2018.
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

    override fun validateCoupon(coupon: String) {
        mValidateCouponView.showValidateLoading()
        val token = "Token token=${EVUserSession.instance.authToken}"
        mEstacionVitalRemoteDataSource.validateCoupon(token, coupon, object: IEVValidateCouponCallBack{
            override fun onTokenExpired() {
                mValidateCouponView.hideLoading()
                expireSession()
            }

            override fun onSuccess(response: EVValidateCouponResponse) {
                mValidateCouponView.hideLoading()
                mValidateCouponView.isValid(response.valid)
            }

            override fun onFailure() {
                mValidateCouponView.hideLoading()
                mValidateCouponView.showErrorLoading()
            }
        })
    }

    override fun createNewExamination(specialty: String, service_type: String) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mValidateCouponView.showCreatingRoomLoading()
        mEstacionVitalRemoteDataSource.createNewExamination(token, specialty, service_type, object: IEVCreateNewExaminationCallBack {
            override fun onTokenExpired() {
                mValidateCouponView.hideLoading()
                expireSession()
            }

            override fun onSuccess(response: EVCreateNewExaminationResponse) {
                mValidateCouponView.hideLoading()
                mValidateCouponView.getCreatedRoomID(response.room)
            }

            override fun onFailure() {
                mValidateCouponView.hideLoading()
                mValidateCouponView.showErrorLoading()
            }
        })
    }

    override fun joinEVTwilioRoom(room_id: String) {
        mEVTwilioChatRemoteDataSource.findChannelByID(room_id, object: IEVTwilioFindChannelByIDCallback{
            override fun onSuccess(channel: Channel) {
                mEVTwilioChatRemoteDataSource.joinChannel(channel,object: IEVTwilioJoinChannelCallBack {
                    override fun onSuccess() {
                        mValidateCouponView.hideLoading()
                        mValidateCouponView.prepareToNavigateToChat(channel)
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