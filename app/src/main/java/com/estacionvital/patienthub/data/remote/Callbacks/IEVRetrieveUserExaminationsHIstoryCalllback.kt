package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVRetrieveUserExaminationResponse

/**
 * Created by dusti on 16/03/2018.
 */
interface IEVRetrieveUserExaminationsHIstoryCalllback {
    fun onSuccess(response: EVRetrieveUserExaminationResponse)
    fun onFailure()
    fun onTokenExpired()
}