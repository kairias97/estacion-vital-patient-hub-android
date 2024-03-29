package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.BlogArticle

/**
 * Created by kevin on 9/3/2018.
 */
interface IArticleSelectionView : IBaseView{
    fun showArticlesLoadingProgress()
    fun hideArticlesLoadingProgress()
    fun navigateToWebArticle(article: BlogArticle)
    fun updateArticlesListUI(articles: MutableList<BlogArticle>, pageSize: Int)
    fun showInternalServerError()
    fun updatePagerIndicator(currentPage: Int, maxPage: Int)
    fun getCurrentArticlePage(): Int
    fun getMaxArticlePage(): Int
    fun getPageSize(): Int
    fun requestNextPage()
    fun requestPreviousPage()
    fun showPreviousPageButton()
    fun showNextPageButton()
    fun hidePreviousPageButton()
    fun hideNextPageButton()
}