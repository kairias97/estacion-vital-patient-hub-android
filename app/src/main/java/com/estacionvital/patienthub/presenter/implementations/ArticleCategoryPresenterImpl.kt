package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.IGetArticleCategoriesCallback
import com.estacionvital.patienthub.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthub.model.ArticleCategoriesResponse
import com.estacionvital.patienthub.presenter.IArticleCategoryPresenter
import com.estacionvital.patienthub.ui.fragmentViews.IArticleCategoryFragmentView

/**
 * Created by kevin on 6/3/2018.
 */
//Implementacion de presenter para la pantalla de categorias de articulos
class ArticleCategoryPresenterImpl: IArticleCategoryPresenter {
    private val mCategoriesView: IArticleCategoryFragmentView
    private val mEVBlogRemoteDataSource: EVBlogRemoteDataSource

    constructor(categoriesView: IArticleCategoryFragmentView,
                evBlogRemoteDataSource: EVBlogRemoteDataSource){
        this.mCategoriesView = categoriesView
        this.mEVBlogRemoteDataSource = evBlogRemoteDataSource
    }


    override fun loadCategories() {
        mCategoriesView.showLoadingProgress()
        mEVBlogRemoteDataSource.getArticleCategories(object: IGetArticleCategoriesCallback {
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