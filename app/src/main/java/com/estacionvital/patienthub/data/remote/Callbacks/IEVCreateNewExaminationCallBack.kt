package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.EVCreateNewExaminationResponse

/**
 * Created by dusti on 19/03/2018.
 */
interface IEVCreateNewExaminationCallBack {
    fun onSuccess(response: EVCreateNewExaminationResponse)
    fun onFailure()
    fun onTokenExpired()
    fun onChatCreationDenied()
}