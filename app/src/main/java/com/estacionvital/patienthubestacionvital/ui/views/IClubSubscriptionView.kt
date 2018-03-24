package com.estacionvital.patienthubestacionvital.ui.views

import com.estacionvital.patienthubestacionvital.model.EVClub

/**
 * Created by dusti on 26/02/2018.
 */
interface IClubSubscriptionView {
    fun showInternalErrorMessage()
    fun showLimitSubscriptionMessage()
    fun showRetrievingCatalogProcess()
    fun hideRetrievingCatalogProcess()
    fun showClubSubscription(clubs: MutableList<EVClub>)
    fun updateClubSubscriptionView(club: EVClub)
    fun getSelectedClubsCount(): Int
    fun getSelectedClubs(): List<EVClub>
    fun showSubscriptionsRequiredMessage()
    fun navigateToEVRegistration()
    fun showSubscriptionProgress(clubName:String)
    fun hideSubscriptionProgress()
    fun showSubscriptionSuccessMessage()
    fun showNewClubSuccessMessage(clubName:String)
    fun showCustomWSMessage(msg: String)
    fun showActiveSubscriptionsProgress()
    fun hideActiveSubscriptionsProgress()
}