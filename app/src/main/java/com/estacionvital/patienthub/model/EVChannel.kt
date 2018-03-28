package com.estacionvital.patienthub.model

import android.os.Parcel
import android.os.Parcelable
import com.twilio.chat.Channel

/**
 * Created by dusti on 17/03/2018.
 */
class EVChannel() : Parcelable{
    var unique_name: String = ""
    var isFinished: Boolean = true
    var type: String = ""
    var specialty: String = ""
    var twilioChannel: Channel ?= null
    var doctorName: String = ""

    constructor(parcel: Parcel) : this() {
        unique_name = parcel.readString()
        isFinished = parcel.readByte() != 0.toByte()
        type = parcel.readString()
        specialty = parcel.readString()
        twilioChannel = parcel.readParcelable(Channel::class.java.classLoader)
        doctorName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(unique_name)
        parcel.writeByte(if (isFinished) 1 else 0)
        parcel.writeString(type)
        parcel.writeString(specialty)
        parcel.writeParcelable(twilioChannel, flags)
        parcel.writeString(doctorName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EVChannel> {
        override fun createFromParcel(parcel: Parcel): EVChannel {
            return EVChannel(parcel)
        }

        override fun newArray(size: Int): Array<EVChannel?> {
            return arrayOfNulls(size)
        }
    }

}