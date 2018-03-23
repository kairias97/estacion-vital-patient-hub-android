package com.estacionvital.patienthubestacionvital.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 7/3/2018.
 */
data class BlogArticle(@SerializedName("id") val id: Int,
                       @SerializedName("url") val url: String,
                       @SerializedName("title") val title: String,
                       @SerializedName("date") val date: String,
                       @SerializedName("thumbnail") val thumbnail: String?,
                       @SerializedName("author") val author: BlogAuthor): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(BlogAuthor::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(url)
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(thumbnail)
        parcel.writeParcelable(author, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BlogArticle> {
        override fun createFromParcel(parcel: Parcel): BlogArticle {
            return BlogArticle(parcel)
        }

        override fun newArray(size: Int): Array<BlogArticle?> {
            return arrayOfNulls(size)
        }
    }

}