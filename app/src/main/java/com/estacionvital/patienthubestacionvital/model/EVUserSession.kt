package com.estacionvital.patienthubestacionvital.model

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
        if(name == userProfile.name && lastName == userProfile.last_name && email == userProfile.email){
            return true
        }
        else{
            return false
        }
    }

    companion object {
        val instance: EVUserSession by lazy { EVUserSession()}
    }
}