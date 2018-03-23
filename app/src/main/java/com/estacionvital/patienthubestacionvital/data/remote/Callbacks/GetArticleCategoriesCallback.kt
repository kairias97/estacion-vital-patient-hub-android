package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.estacionvital.patienthubestacionvital.model.ArticleCategoriesResponse

/**
 * Created by kevin on 6/3/2018.
 */
interface GetArticleCategoriesCallback {
    fun onSuccess(response: ArticleCategoriesResponse)
    fun onFailure()
}