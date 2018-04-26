package com.estacionvital.patienthub.model

import com.estacionvital.patienthub.util.MAX_TOTAL_SUSCRIPTIONS

/**
 * Created by dusti on 27/02/2018.
 */
class RegistrationSession {
    var phoneNumber: String = ""
    var evClubsRegistrationLimit: Int = 0
    var totalClubsRegistrationLimit: Int  = 0
    var netmobileTotalSuscriptions: Int = 0

    private constructor()
    companion object {
        val instance: RegistrationSession by lazy { RegistrationSession()}
    }

}
