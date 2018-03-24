package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.EVRetrieveUserExaminationResponse

/**
 * Created by dusti on 16/03/2018.
 */
interface IEVRetrieveUserExaminationsHIstoryCalllback {
    fun onSuccess(response: EVRetrieveUserExaminationResponse)
    fun onFailure()
}