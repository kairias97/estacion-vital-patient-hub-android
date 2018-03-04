package com.estacionvital.patienthub.model

/**
 * Created by kevin on 28/2/2018.
 */
class EVUserSession {
    var phoneNumber: String = ""
    var authToken: String = ""

    lateinit var userProfile: EVUserProfile
    private constructor()

    companion object {
        val instance: EVUserSession by lazy { EVUserSession()}
    }
}