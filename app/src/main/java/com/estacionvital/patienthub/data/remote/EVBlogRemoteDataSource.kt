package com.estacionvital.patienthub.data.remote

import android.os.Build
import android.util.Log
import com.estacionvital.patienthub.BuildConfig
import com.estacionvital.patienthub.data.api.EVBlogAPI
import com.estacionvital.patienthub.data.remote.Callbacks.GetArticleCategoriesCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IArticlesByCategoryCallback
import com.estacionvital.patienthub.model.ArticleCategoriesResponse
import com.estacionvital.patienthub.model.ArticlesByCategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by kevin on 6/3/2018.
 */
class EVBlogRemoteDataSource {
    private constructor()
    companion object {
        val INSTANCE: EVBlogRemoteDataSource by lazy { EVBlogRemoteDataSource()}
    }
    fun getArticleCategories(callback: GetArticleCategoriesCallback){
        val authCall = EVBlogAPI.instance.service!!.getArticleCategories()
        authCall.enqueue(object: Callback<ArticleCategoriesResponse>{
            override fun onFailure(call: Call<ArticleCategoriesResponse>?, t: Throwable?) {
                if (BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("GetCategories error", t.toString())
                }
                callback.onFailure()
            }

            override fun onResponse(call: Call<ArticleCategoriesResponse>?, response: Response<ArticleCategoriesResponse>?) {

                when (response?.code()) {

                    200 -> {
                        callback.onSuccess(response.body()!!)
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("GetCategories error" + response!!.code().toString(), response!!.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

        })
    }

    fun getArticlesByCategory(categoryID: Int, callback: IArticlesByCategoryCallback){
        val authCall = EVBlogAPI.instance.service!!.getArticlesByCategory(categoryID)
        authCall.enqueue(object:Callback<ArticlesByCategoryResponse>{
            override fun onFailure(call: Call<ArticlesByCategoryResponse>?, t: Throwable?) {
                if (BuildConfig.BUILD_TYPE == "debug") {
                   Log.e("getArticlesByCategory", t.toString())
                }
                callback.onFailure()
            }

            override fun onResponse(call: Call<ArticlesByCategoryResponse>?, response: Response<ArticlesByCategoryResponse>?) {
                when (response?.code()) {
                    200 -> callback.onSuccess(response.body()!!)
                    else -> {
                        callback.onFailure()
                    }
                }
            }


        })
    }
}