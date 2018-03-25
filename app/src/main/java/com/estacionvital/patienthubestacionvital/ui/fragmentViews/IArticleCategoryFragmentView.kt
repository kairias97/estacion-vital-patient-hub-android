package com.estacionvital.patienthubestacionvital.ui.fragmentViews

import com.estacionvital.patienthubestacionvital.model.ArticleCategory

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