package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.ArticleCategoriesResponse

/**
 * Created by kevin on 6/3/2018.
 */
interface IGetArticleCategoriesCallback {
    fun onSuccess(response: ArticleCategoriesResponse)
    fun onFailure()
}