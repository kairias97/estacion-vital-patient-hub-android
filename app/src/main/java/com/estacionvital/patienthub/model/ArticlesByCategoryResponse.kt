package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 8/3/2018.
 */
data class ArticlesByCategoryResponse(@SerializedName("status") val status: String,
                                      @SerializedName("count") val count: Int,
                                      @SerializedName("posts") val posts: List<BlogArticle>)