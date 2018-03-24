package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.ArticlesByCategoryResponse

/**
 * Created by kevin on 8/3/2018.
 */
interface IArticlesByCategoryCallback {
    fun onSuccess(response: ArticlesByCategoryResponse)
    fun onFailure()
}