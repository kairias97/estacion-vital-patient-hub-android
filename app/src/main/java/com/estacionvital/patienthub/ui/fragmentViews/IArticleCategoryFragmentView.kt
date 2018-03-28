package com.estacionvital.patienthub.ui.fragmentViews

import com.estacionvital.patienthub.model.ArticleCategory

/**
 * Created by kevin on 6/3/2018.
 */
interface IArticleCategoryFragmentView {
    fun showLoadingProgress()
    fun showInternalServerError()
    fun showCustomMessage(msg: String)
    fun setArticleCategories(categories: MutableList<ArticleCategory>)
    fun hideLoadingProgress()
    fun navigateToArticlesActivity(category: ArticleCategory)
}