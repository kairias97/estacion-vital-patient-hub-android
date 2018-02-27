package com.estacionvital.patienthub.presenter

import com.estacionvital.patienthub.model.EVClub

/**
 * Created by dusti on 26/02/2018.
 */
interface ISuscriptionCatalogPresenter {
    fun retrieveCatalog(auth_credential: String)
    fun retrieveLimit(auth_credential: String)
    fun retrieveActive(number: String, auth_credential: String, catalog: List<EVClub>)
}