package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.model.ArticleCategoriesResponse
import com.estacionvital.patienthub.model.ArticlesByCategoryResponse
import com.estacionvital.patienthub.util.URL_EV_BLOG_GET_ARTICLES_BY_CATEGORY
import com.estacionvital.patienthub.util.URL_EV_BLOG_GET_CATEGORIES
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by kevin on 6/3/2018.
 */
interface IEVBlogService {
    @GET(URL_EV_BLOG_GET_CATEGORIES)
    fun getArticleCategories(): Call<ArticleCategoriesResponse>
    @GET(URL_EV_BLOG_GET_ARTICLES_BY_CATEGORY)
    fun getArticlesByCategory(@Query("id") id: Int): Call<ArticlesByCategoryResponse>
}