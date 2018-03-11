package com.estacionvital.patienthub.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthub.model.ArticleCategory
import com.estacionvital.patienthub.presenter.IArticleCategoryPresenter
import com.estacionvital.patienthub.presenter.implementations.ArticleCategoryPresenterImpl
import com.estacionvital.patienthub.ui.adapters.ArticleCategoryAdapter
import com.estacionvital.patienthub.ui.fragmentViews.IArticleCategoryFragment

/**
 * Created by kevin on 6/3/2018.
 */
class ArticleCategoryFragment: Fragment(), IArticleCategoryFragment,
        ArticleCategoryAdapter.OnCategorySelectedListener {
    override fun onCategoryItemClicked(category: ArticleCategory) {
        this.mListener?.onCategorySelected(category)
    }


    private var mListener: OnArticleCategoryInteraction? = null
    private lateinit var mCategoriesRecyclerView: RecyclerView
    private lateinit var mPresenter:IArticleCategoryPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter = ArticleCategoryPresenterImpl(this, EVBlogRemoteDataSource.INSTANCE)
        mPresenter.loadCategories()
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater!!.inflate(R.layout.fragment_article_category_list, container, false)
        mCategoriesRecyclerView = view!!.findViewById(R.id.recycler_article_categories)
        mCategoriesRecyclerView.layoutManager = LinearLayoutManager(activity)
        mCategoriesRecyclerView.setHasFixedSize(true)
        mCategoriesRecyclerView.adapter = ArticleCategoryAdapter( ArrayList(), this)

        return view
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity.title = getString(R.string.title_fragment_categories)

    }
    companion object {
        fun newInstance(listener: OnArticleCategoryInteraction): ArticleCategoryFragment {
            val fragment =  ArticleCategoryFragment()
            fragment.mListener = listener
            return fragment
        }
    }
    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnArticleCategoryInteraction {
        fun onLoadingCategories()
        fun onCategoriesLoaded()
        fun onError(msg: String)
        fun showCustomMessage(msg: String)
        fun onCategorySelected(category: ArticleCategory)
    }

    override fun showLoadingProgress() {
        this.mListener?.onLoadingCategories()
    }

    override fun showCustomMessage(msg: String) {
        this.mListener?.showCustomMessage(msg)

    }

    override fun showInternalServerError() {
        this.mListener?.onError(getString(R.string.generic_500_error))
    }

    override fun navigateToArticlesActivity(category: ArticleCategory) {
        this.mListener?.onCategorySelected(category)
    }

    override fun setArticleCategories(categories: MutableList<ArticleCategory>) {
        (this.mCategoriesRecyclerView.adapter as ArticleCategoryAdapter).setCategories(categories)
        (this.mCategoriesRecyclerView.adapter as ArticleCategoryAdapter).notifyDataSetChanged()
    }

    override fun hideLoadingProgress() {
        this.mListener?.onCategoriesLoaded()
    }
}