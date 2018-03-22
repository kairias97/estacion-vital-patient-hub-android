package com.estacionvital.patienthub.ui.views

/**
 * Created by dusti on 22/03/2018.
 */
interface IValidateCouponView {
    fun showValidateLoading()
    fun hideLoading()
    fun showErrorLoading()
    fun isValid(isValid: Boolean)
}