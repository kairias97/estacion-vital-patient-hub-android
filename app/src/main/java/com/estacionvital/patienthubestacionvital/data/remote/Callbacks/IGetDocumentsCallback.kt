package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.DocumentsResponse

/**
 * Created by kevin on 24/3/2018.
 */
interface IGetDocumentsCallback {
    fun onSuccess(response: DocumentsResponse)
    fun onFailure()
    fun onTokenExpired()
}