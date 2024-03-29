package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IArticlesCallback
import com.estacionvital.patienthub.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthub.model.ArticlesResponse
import com.estacionvital.patienthub.presenter.IArticleSelectionPresenter
import com.estacionvital.patienthub.ui.views.IArticleSelectionView
import com.estacionvital.patienthub.util.PAGE_SIZE_BLOG_ARTICLES

/**
 * Created by kevin on 9/3/2018.
 */
//Implementacion de presenter para la pantalla de articulos del blog
class ArticleSelectionPresenterImpl: IArticleSelectionPresenter {
    override fun onNextPageRequested() {
        mArticleSelectionView.requestNextPage()
        updatePaginatorState()

    }
    //For hiding or showing navigation buttons
    private fun updatePaginatorState() {
        val currentPage = mArticleSelectionView.getCurrentArticlePage()
        val pageCount = mArticleSelectionView.getMaxArticlePage()
        if (currentPage == 1) {
            mArticleSelectionView.hidePreviousPageButton()
        } else {
            mArticleSelectionView.showPreviousPageButton()
        }
        if ( currentPage == pageCount) {
            mArticleSelectionView.hideNextPageButton()
        } else {
            mArticleSelectionView.showNextPageButton()
        }
        mArticleSelectionView.updatePagerIndicator(currentPage, pageCount)
    }
    override fun onPreviousPageRequested() {
        mArticleSelectionView.requestPreviousPage()
        updatePaginatorState()
    }

    private val mArticleSelectionView: IArticleSelectionView
    private val mEVBlogRemoteDataSource: EVBlogRemoteDataSource

    constructor(view: IArticleSelectionView, dataSource: EVBlogRemoteDataSource){
        this.mArticleSelectionView = view
        this.mEVBlogRemoteDataSource = dataSource
    }
    //cargar articulos por categoria
    override fun loadArticles(categoryID: Int) {
        this.mArticleSelectionView.showArticlesLoadingProgress()
        this.mEVBlogRemoteDataSource.getArticlesByCategory(categoryID, object: IArticlesCallback{
            override fun onSuccess(response: ArticlesResponse) {
                mArticleSelectionView.hideArticlesLoadingProgress()
                if (response.status == "ok") {
                    mArticleSelectionView.updateArticlesListUI(response.posts.toMutableList(), PAGE_SIZE_BLOG_ARTICLES)
                    val currentPage = mArticleSelectionView.getCurrentArticlePage()
                    val pageCount = mArticleSelectionView.getMaxArticlePage()
                    mArticleSelectionView.updatePagerIndicator(currentPage, pageCount)

                    if (pageCount > 1) {
                        mArticleSelectionView.showNextPageButton()
                    }
                } else {
                    mArticleSelectionView.showInternalServerError()
                }
            }

            override fun onFailure() {
                mArticleSelectionView.hideArticlesLoadingProgress()
                mArticleSelectionView.showInternalServerError()
            }

        })
    }
}