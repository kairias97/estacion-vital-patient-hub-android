package com.estacionvital.patienthub.model

/**
 * Created by dusti on 27/02/2018.
 */
class RegistrationSession {
    var phoneNumber: String = ""
    var clubsRegistrationLimit: Int = 0

    companion object {
        val instance: RegistrationSession by lazy { RegistrationSession()}
    }
}
