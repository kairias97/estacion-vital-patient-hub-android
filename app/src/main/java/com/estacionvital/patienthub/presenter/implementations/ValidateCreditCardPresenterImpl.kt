package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.Callbacks.IEVCreateNewExaminationCallBack
import com.estacionvital.patienthub.data.remote.Callbacks.IEVPaymentCreditCardCallBack
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioFindChannelByIDCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVTwilioJoinChannelCallBack
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.model.EVCreateNewExaminationResponse
import com.estacionvital.patienthub.model.EVCreditCardResponse
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.IValidateCreditCardPresenter
import com.estacionvital.patienthub.ui.views.IValidateCreditCardView
import com.estacionvital.patienthub.util.PAYMENT_TYPE_CREDIT_CARD
import com.twilio.chat.Channel

/**
 * Created by dusti on 07/04/2018.
 */
/**
 * Presenter para validacion de tarjeta de credito
 */
class ValidateCreditCardPresenterImpl: IValidateCreditCardPresenter {
    private val mValidateCreditCardView: IValidateCreditCardView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource
    private val mEVTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(validateCreditCardView: IValidateCreditCardView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource, evTwilioChatRemoteDataSource: EVTwilioChatRemoteDataSource,
                sharedPrefManager: SharedPrefManager){
        this.mValidateCreditCardView = validateCreditCardView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
        this.mEVTwilioChatRemoteDataSource = evTwilioChatRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }
    //expirar sesion de usuario
    fun expireSession() {
        mSharedPrefManager.clearPreferences()
        mValidateCreditCardView.showExpirationMessage()
    }
    //validar tarjeta de credito
    override fun validateCreditCard(holder: String, expYear: String, expMonth: String, number: String, cvc: String, specialty: String, service_type: String) {
        mValidateCreditCardView.showProcessingCreditCard()
        val token = "Token token=${EVUserSession.instance.authToken}"
        mEstacionVitalRemoteDataSource.paymentCreditCard(token, holder, expYear, expMonth, number, cvc, object: IEVPaymentCreditCardCallBack{
            override fun onSuccess(response: EVCreditCardResponse) {
                mValidateCreditCardView.hideLoading()
                if(response.status == "success"){
                    createNewExamination(specialty, service_type, PAYMENT_TYPE_CREDIT_CARD, "", response.order_id)
                }
                else{
                    mValidateCreditCardView.showErrorProcessingCreditCard(response.message)
                }
            }

            override fun onTokenExpired() {
                mValidateCreditCardView.hideLoading()
                expireSession()
            }

            override fun onFailure() {
                mValidateCreditCardView.hideLoading()
                mValidateCreditCardView.showErrorMessage()
            }
        })
    }
    //crear una nueva examinacion de chat
    override fun createNewExamination(specialty: String, serviceType: String, type: String, code: String, order_id: String) {
        val token = "Token token=${EVUserSession.instance.authToken}"
        mValidateCreditCardView.showCreatingRoomLoading()
        mEstacionVitalRemoteDataSource.createNewExamination(token, specialty, serviceType, type, code, order_id, object: IEVCreateNewExaminationCallBack {
            override fun onChatCreationDenied() {
                mValidateCreditCardView.hideLoading()
            }

            override fun onTokenExpired() {
                mValidateCreditCardView.hideLoading()
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
                mValidateCreditCardView.hideLoading()
                mValidateCreditCardView.showErrorMessage()
            }
        })
    }
    //unir al usuario a un ev channel obtenido del api
    override fun joinEVTwilioRoom(evChannel: EVChannel) {
        mEVTwilioChatRemoteDataSource.findChannelByID(evChannel.unique_name, object: IEVTwilioFindChannelByIDCallback {
            override fun onSuccess(channel: Channel) {
                mEVTwilioChatRemoteDataSource.joinChannel(channel,object: IEVTwilioJoinChannelCallBack {
                    override fun onSuccess() {
                        mValidateCreditCardView.hideLoading()
                        evChannel.twilioChannel = channel
                        mValidateCreditCardView.prepareToNavigateToChat(evChannel)
                    }

                    override fun onFailure() {
                        mValidateCreditCardView.hideLoading()
                        mValidateCreditCardView.showErrorMessage()
                    }
                })
            }

            override fun onFailure() {
                mValidateCreditCardView.hideLoading()
                mValidateCreditCardView.showErrorMessage()
            }
        })
    }

}