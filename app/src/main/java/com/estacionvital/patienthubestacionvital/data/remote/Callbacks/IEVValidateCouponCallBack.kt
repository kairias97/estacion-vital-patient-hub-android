package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.EVValidateCouponResponse

/**
 * Created by dusti on 22/03/2018.
 */
interface IEVValidateCouponCallBack {
    fun onSuccess(response: EVValidateCouponResponse)
    fun onFailure()
}