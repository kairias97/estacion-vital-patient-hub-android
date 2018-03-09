package com.estacionvital.patienthub.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.BlogArticle

/**
 * Created by kevin on 8/3/2018.
 */
class BlogArticleAdapter: RecyclerView.Adapter<BlogArticleViewHolder> {

    private var mArticles: MutableList<BlogArticle>
    private var mListener: OnArticleInteractionListener

    interface OnArticleInteractionListener{
        fun onArticleItemClicked(article: BlogArticle)
    }

    constructor(articles: MutableList<BlogArticle>, listener: OnArticleInteractionListener): super() {
        this.mArticles = articles
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BlogArticleViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return BlogArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.mArticles.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_blog_article
    }

    fun setArticles(articles: MutableList<BlogArticle>) {
        this.mArticles = articles
    }
    override fun onBindViewHolder(holder: BlogArticleViewHolder?, position: Int) {
        (holder as BlogArticleViewHolder).bindData( this.mArticles[position], this.mListener)
    }
}