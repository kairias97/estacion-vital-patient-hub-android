package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.EVChannel

/**
 * Created by dusti on 07/04/2018.
 */
interface IValidateCreditCardView {
    fun showProcessingCreditCard()
    fun showCreatingRoomLoading()
    fun showErrorMessage()
    fun showErrorProcessingCreditCard(msg: String)
    fun hideLoading()
    fun prepareToNavigateToChat(data: EVChannel)
    fun showExpirationMessage()
}