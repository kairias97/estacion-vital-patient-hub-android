package com.estacionvital.patienthub.model

/**
 * Created by kevin on 28/2/2018.
 */
class EVUserSession {
    var phoneNumber: String = ""
    var authToken: String = ""
    var twilioToken: String = ""

    lateinit var userProfile: EVUserProfile
    private constructor()

    fun verifyProfileData(name: String, lastName: String, email: String): Boolean{
        return name == userProfile.name && lastName == userProfile.last_name && email == userProfile.email
    }

    companion object {
        val instance: EVUserSession by lazy { EVUserSession()}
    }
}