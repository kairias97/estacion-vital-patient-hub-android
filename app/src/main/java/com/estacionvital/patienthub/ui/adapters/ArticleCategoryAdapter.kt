package com.estacionvital.patienthub.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.ArticleCategory

/**
 * Created by kevin on 5/3/2018.
 */
class ArticleCategoryAdapter: RecyclerView.Adapter<ArticleCategoryViewHolder> {
    private  var mArticleCategories: MutableList<ArticleCategory>
    private val mListener: OnCategorySelectedListener

    interface OnCategorySelectedListener{
        fun onCategoryItemClicked(category: ArticleCategory)
    }

    constructor(articles: List<ArticleCategory>, listener: OnCategorySelectedListener){
        this.mArticleCategories = articles.toMutableList()
        this.mListener = listener

    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ArticleCategoryViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)

        return ArticleCategoryViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_article_category
    }

    override fun getItemCount(): Int {
        return mArticleCategories.size
    }

    fun setCategories(categories: MutableList<ArticleCategory>) {
        this.mArticleCategories = categories
    }

    override fun onBindViewHolder(holder: ArticleCategoryViewHolder?, position: Int) {
        (holder as ArticleCategoryViewHolder).bindData(this.mArticleCategories.get(position), this.mListener)
    }

}