package com.estacionvital.patienthub.ui.views

import com.twilio.chat.Channel

/**
 * Created by dusti on 22/03/2018.
 */
interface IValidateCouponView : IBaseView{
    fun showValidateLoading()
    fun showCreatingRoomLoading()
    fun getCreatedRoomID(data: String)
    fun hideLoading()
    fun showErrorLoading()
    fun isValid(isValid: Boolean)
    fun prepareToNavigateToChat(data: Channel)
}