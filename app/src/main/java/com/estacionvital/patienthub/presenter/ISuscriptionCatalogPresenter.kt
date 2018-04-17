package com.estacionvital.patienthub.presenter

import com.estacionvital.patienthub.model.EVClub

/**
 * Created by dusti on 26/02/2018.
 */
interface ISuscriptionCatalogPresenter {
    fun retrieveCatalog(number: String,auth_credential: String)
    fun retrieveLimit(isLoggedInUser: Boolean, auth_credential: String)
    fun retrieveActive(number: String, auth_credential: String, catalog: List<EVClub>)
    fun validateSelectedClub(club: EVClub)
    fun validateSubscriptions(isLoggedInUser: Boolean)
}