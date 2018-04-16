package com.estacionvital.patienthub.data.remote.Callbacks

import com.estacionvital.patienthub.model.ArticlesResponse

/**
 * Created by kevin on 8/3/2018.
 */
interface IArticlesCallback {
    fun onSuccess(response: ArticlesResponse)
    fun onFailure()
}