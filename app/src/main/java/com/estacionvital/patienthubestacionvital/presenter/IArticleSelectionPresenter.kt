package com.estacionvital.patienthubestacionvital.presenter

/**
 * Created by kevin on 9/3/2018.
 */
interface IArticleSelectionPresenter {
    fun loadArticles(categoryID: Int)
    fun onNextPageRequested()
    fun onPreviousPageRequested()
}