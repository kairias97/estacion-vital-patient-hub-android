package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.BlogArticle

/**
 * Created by kevin on 9/3/2018.
 */
interface IArticleSelectionView {
    fun showArticlesLoadingProgress()
    fun hideArticlesLoadingProgress()
    fun navigateToWebArticle(article: BlogArticle)
    fun updateArticlesListUI(articles: MutableList<BlogArticle>)
    fun showInternalServerError()
}