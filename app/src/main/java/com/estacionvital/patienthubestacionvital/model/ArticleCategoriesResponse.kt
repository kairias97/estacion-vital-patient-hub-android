package com.estacionvital.patienthubestacionvital.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 6/3/2018.
 */
data class ArticleCategoriesResponse(@SerializedName("status") val status: String,
                                     @SerializedName("count") val count: Int,
                                     @SerializedName("categories") val categories: List<ArticleCategory>)