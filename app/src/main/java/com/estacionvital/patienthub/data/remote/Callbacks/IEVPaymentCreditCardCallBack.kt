package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVCreditCardResponse

/**
 * Created by dusti on 07/04/2018.
 */
interface IEVPaymentCreditCardCallBack {
    fun onSuccess(response: EVCreditCardResponse)
    fun onFailure()
    fun onTokenExpired()
}