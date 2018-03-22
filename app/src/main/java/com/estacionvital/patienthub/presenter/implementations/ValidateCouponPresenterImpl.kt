package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IEVValidateCouponCallBack
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.model.EVValidateCouponResponse
import com.estacionvital.patienthub.presenter.IValidateCouponPresenter
import com.estacionvital.patienthub.ui.views.IValidateCouponView

/**
 * Created by dusti on 22/03/2018.
 */
class ValidateCouponPresenterImpl: IValidateCouponPresenter {

    private val mValidateCouponView: IValidateCouponView
    private val mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    constructor(validateCouponView: IValidateCouponView, estacionVitalRemoteDataSource: EstacionVitalRemoteDataSource){
        this.mValidateCouponView = validateCouponView
        this.mEstacionVitalRemoteDataSource = estacionVitalRemoteDataSource
    }

    override fun validateCoupon(coupon: String) {
        mValidateCouponView.showValidateLoading()
        val token = "Token token=${EVUserSession.instance.authToken}"
        mEstacionVitalRemoteDataSource.validateCoupon(token, coupon, object: IEVValidateCouponCallBack{
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
}