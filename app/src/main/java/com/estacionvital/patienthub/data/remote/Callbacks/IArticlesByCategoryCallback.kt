package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.ArticlesByCategoryResponse

/**
 * Created by kevin on 8/3/2018.
 */
interface IArticlesByCategoryCallback {
    fun onSuccess(response: ArticlesByCategoryResponse)
    fun onFailure()
}