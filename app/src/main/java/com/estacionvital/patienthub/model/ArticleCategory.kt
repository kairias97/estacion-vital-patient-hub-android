package com.estacionvital.patienthub.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 5/3/2018.
 */
data class ArticleCategory(@SerializedName("id") val id: Int,
                           @SerializedName("title") val title: String,
                           @SerializedName("description") val description: String,
                           @SerializedName("post_count") val post_count: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(post_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleCategory> {
        override fun createFromParcel(parcel: Parcel): ArticleCategory {
            return ArticleCategory(parcel)
        }

        override fun newArray(size: Int): Array<ArticleCategory?> {
            return arrayOfNulls(size)
        }
    }
}