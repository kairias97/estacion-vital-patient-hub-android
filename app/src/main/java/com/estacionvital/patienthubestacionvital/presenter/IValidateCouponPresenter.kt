package com.estacionvital.patienthubestacionvital.presenter

/**
 * Created by dusti on 22/03/2018.
 */
interface IValidateCouponPresenter : IBasePresenter{
    fun validateCoupon(coupon: String)
    fun createNewExamination(specialty: String, service_type: String)
    fun joinEVTwilioRoom(room_id: String)
}