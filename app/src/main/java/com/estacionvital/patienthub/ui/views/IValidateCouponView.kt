package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.EVChannel
import com.twilio.chat.Channel

/**
 * Created by dusti on 22/03/2018.
 */
interface IValidateCouponView : IBaseView{
    fun showValidateLoading()
    fun showCreatingRoomLoading()
    fun showInvalidCouponMessage()
    fun hideLoading()
    fun showErrorLoading()
    fun prepareToNavigateToChat(data: EVChannel)
}