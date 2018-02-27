package com.estacionvital.patienthub.model

/**
 * Created by dusti on 27/02/2018.
 */
class RegistrationSession {
    public var phoneNumber: String = ""
    public var clubsRegistrationLimit: Int = 0

    companion object {
        val instance: RegistrationSession by lazy { RegistrationSession()}
    }
}
