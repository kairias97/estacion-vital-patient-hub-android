package com.estacionvital.patienthubestacionvital.presenter.implementations

import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.GetArticleCategoriesCallback
import com.estacionvital.patienthubestacionvital.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.ArticleCategoriesResponse
import com.estacionvital.patienthubestacionvital.presenter.IArticleCategoryPresenter
import com.estacionvital.patienthubestacionvital.ui.fragmentViews.IArticleCategoryFragment

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