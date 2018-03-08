package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by kevin on 7/3/2018.
 */
data class BlogArticle(@SerializedName("id") val id: Int,
                       @SerializedName("url") val url: String,
                       @SerializedName("title") val title: String,
                       @SerializedName("date") val date: Date,
                       @SerializedName("thumbnail") val thumbnail: String,
                       @SerializedName("author") val author: BlogAuthor)