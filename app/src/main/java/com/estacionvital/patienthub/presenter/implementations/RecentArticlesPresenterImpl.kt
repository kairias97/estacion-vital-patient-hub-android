package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IArticlesCallback
import com.estacionvital.patienthub.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthub.model.ArticlesResponse
import com.estacionvital.patienthub.presenter.IRecentArticlesPresenter
import com.estacionvital.patienthub.ui.views.IArticleSelectionView
import com.estacionvital.patienthub.ui.views.IRecentArticlesView
import com.estacionvital.patienthub.util.RECENT_ARTICLES_PAGE_SIZE

/**
 * Created by kevin on 15/4/2018.
 */
class RecentArticlesPresenterImpl: IRecentArticlesPresenter {


    private val mArticleSelectionView: IRecentArticlesView
    private val mEVBlogRemoteDataSource: EVBlogRemoteDataSource

    constructor(view: IRecentArticlesView, dataSource: EVBlogRemoteDataSource){
        this.mArticleSelectionView = view
        this.mEVBlogRemoteDataSource = dataSource
    }

    override fun loadArticles() {
        this.mArticleSelectionView.showLoadingProgress()
        val page = 1
        this.mEVBlogRemoteDataSource.getRecentArticles(page,RECENT_ARTICLES_PAGE_SIZE, object: IArticlesCallback {
            override fun onSuccess(response: ArticlesResponse) {
                mArticleSelectionView.hideLoadingProgress()
                if (response.status == "ok") {
                    mArticleSelectionView.updateUIList(response.posts.toMutableList(), RECENT_ARTICLES_PAGE_SIZE)

                } else {
                    mArticleSelectionView.showErrorMessage()
                }
            }

            override fun onFailure() {
                mArticleSelectionView.hideLoadingProgress()
                mArticleSelectionView.showErrorMessage()
            }

        })
    }
}