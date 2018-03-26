package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.DocumentsResponse

/**
 * Created by kevin on 24/3/2018.
 */
interface IGetDocumentsCallback {
    fun onSuccess(response: DocumentsResponse)
    fun onFailure()
    fun onTokenExpired()
}