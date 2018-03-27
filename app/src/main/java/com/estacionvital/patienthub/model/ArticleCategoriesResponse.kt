package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 6/3/2018.
 */
data class ArticleCategoriesResponse(@SerializedName("isFinished") val status: String,
                                     @SerializedName("count") val count: Int,
                                     @SerializedName("categories") val categories: List<ArticleCategory>)