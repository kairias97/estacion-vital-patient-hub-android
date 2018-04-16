package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.BlogArticle

/**
 * Created by kevin on 15/4/2018.
 */
interface IRecentArticlesView {
    fun updateUIList(articles: MutableList<BlogArticle>, pageSize: Int)
    fun showErrorMessage()
    fun showLoadingProgress()
    fun hideLoadingProgress()

}