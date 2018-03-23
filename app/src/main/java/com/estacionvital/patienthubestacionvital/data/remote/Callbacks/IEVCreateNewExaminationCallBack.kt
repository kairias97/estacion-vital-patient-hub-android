package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.EVCreateNewExaminationResponse

/**
 * Created by dusti on 19/03/2018.
 */
interface IEVCreateNewExaminationCallBack {
    fun onSuccess(response: EVCreateNewExaminationResponse)
    fun onFailure()
}