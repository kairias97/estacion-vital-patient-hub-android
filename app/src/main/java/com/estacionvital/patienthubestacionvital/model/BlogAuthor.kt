package com.estacionvital.patienthubestacionvital.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 7/3/2018.
 */
data class BlogAuthor(@SerializedName("id") val id: Int,
                      @SerializedName("name") val name:String,
                      @SerializedName("nickname") val nickname:String
                      ): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(nickname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BlogAuthor> {
        override fun createFromParcel(parcel: Parcel): BlogAuthor {
            return BlogAuthor(parcel)
        }

        override fun newArray(size: Int): Array<BlogAuthor?> {
            return arrayOfNulls(size)
        }
    }
}