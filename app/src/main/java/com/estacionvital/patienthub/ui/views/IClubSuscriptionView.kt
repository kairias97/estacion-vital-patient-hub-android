package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.EVClub

/**
 * Created by dusti on 26/02/2018.
 */
interface IClubSuscriptionView {
    fun showInternalErrorMessage()
    fun showLimitSuscriptionMessage()
    fun showRetrievingProcces()
    fun showClubSuscription(clubs: List<EVClub>)
}