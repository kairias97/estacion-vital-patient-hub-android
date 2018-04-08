package com.estacionvital.patienthub.presenter

import com.estacionvital.patienthub.model.EVChannel

/**
 * Created by dusti on 22/03/2018.
 */
interface IValidateCouponPresenter : IBasePresenter{
    fun validateCoupon(coupon: String, specialty: String, serviceType:String)
    fun createNewExamination(specialty: String, serviceType: String, type: String, code: String, order_id: String)
    fun joinEVTwilioRoom(evChannel: EVChannel)
}