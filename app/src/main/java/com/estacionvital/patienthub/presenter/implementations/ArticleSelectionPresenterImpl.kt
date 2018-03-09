package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IArticlesByCategoryCallback
import com.estacionvital.patienthub.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthub.model.ArticlesByCategoryResponse
import com.estacionvital.patienthub.presenter.IArticleSelectionPresenter
import com.estacionvital.patienthub.ui.views.IArticleSelectionView

/**
 * Created by kevin on 9/3/2018.
 */
class ArticleSelectionPresenterImpl: IArticleSelectionPresenter {
    private val mArticleSelectionView: IArticleSelectionView
    private val mEVBlogRemoteDataSource: EVBlogRemoteDataSource

    constructor(view: IArticleSelectionView, dataSource: EVBlogRemoteDataSource){
        this.mArticleSelectionView = view
        this.mEVBlogRemoteDataSource = dataSource
    }
    override fun loadArticles(categoryID: Int) {
        this.mArticleSelectionView.showArticlesLoadingProgress()
        this.mEVBlogRemoteDataSource.getArticlesByCategory(categoryID, object: IArticlesByCategoryCallback{
            override fun onSuccess(response: ArticlesByCategoryResponse) {
                mArticleSelectionView.hideArticlesLoadingProgress()
                if (response.status == "ok") {
                    mArticleSelectionView.updateArticlesListUI(response.posts.toMutableList())
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