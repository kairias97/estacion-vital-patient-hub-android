package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.SuscriptionCatalogResponse

/**
 * Created by dusti on 26/02/2018.
 */
interface ISuscriptionCatalogCallback {
    fun onSucces(response: List<SuscriptionCatalogResponse>)
    fun onFailure()
}