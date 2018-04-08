package com.estacionvital.patienthub.presenter

import com.estacionvital.patienthub.model.EVChannel

/**
 * Created by dusti on 07/04/2018.
 */
interface IValidateCreditCardPresenter {
    fun validateCreditCard(holder: String, expYear: String, expMonth: String, number: String, cvc: String, specialty: String, service_type: String)
    fun createNewExamination(specialty: String, serviceType: String, type: String, code: String, order_id: String)
    fun joinEVTwilioRoom(evChannel: EVChannel)
}