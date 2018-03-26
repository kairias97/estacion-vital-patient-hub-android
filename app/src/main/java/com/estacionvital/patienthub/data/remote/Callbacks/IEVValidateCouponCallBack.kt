package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVValidateCouponResponse

/**
 * Created by dusti on 22/03/2018.
 */
interface IEVValidateCouponCallBack {
    fun onSuccess(response: EVValidateCouponResponse)
    fun onFailure()
    fun onTokenExpired()
}