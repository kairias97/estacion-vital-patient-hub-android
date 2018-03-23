package com.estacionvital.patienthubestacionvital.presenter

import com.estacionvital.patienthubestacionvital.model.EVClub

/**
 * Created by dusti on 26/02/2018.
 */
interface ISuscriptionCatalogPresenter {
    fun retrieveCatalog(number: String,auth_credential: String)
    fun retrieveLimit(number: String, auth_credential: String)
    fun retrieveActive(number: String, auth_credential: String, catalog: List<EVClub>)
    fun validateSelectedClub(club: EVClub)
    fun validateSubscriptions()
}