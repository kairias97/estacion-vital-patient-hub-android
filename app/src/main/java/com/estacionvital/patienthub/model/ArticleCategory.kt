package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 5/3/2018.
 */
data class ArticleCategory(@SerializedName("id") val id: Int,
                           @SerializedName("title") val title: String,
                           @SerializedName("description") val description: String,
                           @SerializedName("post_count") val post_count: Int) {
}