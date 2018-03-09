package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.GetArticleCategoriesCallback
import com.estacionvital.patienthub.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthub.model.ArticleCategoriesResponse
import com.estacionvital.patienthub.presenter.IArticleCategoryPresenter
import com.estacionvital.patienthub.ui.fragmentViews.IArticleCategoryFragment

/**
 * Created by kevin on 6/3/2018.
 */
class ArticleCategoryPresenterImpl: IArticleCategoryPresenter {
    private val mCategoriesView: IArticleCategoryFragment
    private val mEVBlogRemoteDataSource: EVBlogRemoteDataSource

    constructor(categoriesView: IArticleCategoryFragment,
                evBlogRemoteDataSource: EVBlogRemoteDataSource){
        this.mCategoriesView = categoriesView
        this.mEVBlogRemoteDataSource = evBlogRemoteDataSource
    }


    override fun loadCategories() {
        mCategoriesView.showLoadingProgress()
        mEVBlogRemoteDataSource.getArticleCategories(object: GetArticleCategoriesCallback {
            override fun onSuccess(response: ArticleCategoriesResponse) {
                mCategoriesView.hideLoadingProgress()
                if (response.status == "ok") {
                    mCategoriesView.setArticleCategories(response.categories.toMutableList())
                } else {
                    mCategoriesView.showInternalServerError()
                }

            }

            override fun onFailure() {
                mCategoriesView.hideLoadingProgress()
                mCategoriesView.showInternalServerError()
            }

        })
    }

}