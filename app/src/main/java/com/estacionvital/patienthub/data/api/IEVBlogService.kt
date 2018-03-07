package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.model.ArticleCategoriesResponse
import com.estacionvital.patienthub.util.URL_EV_BLOG_GET_CATEGORIES
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by kevin on 6/3/2018.
 */
interface IEVBlogService {
    @GET(URL_EV_BLOG_GET_CATEGORIES)
    fun getArticleCategories(): Call<ArticleCategoriesResponse>
}