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
    private var mArticlesPage: MutableList<BlogArticle>
    private var mListener: OnArticleInteractionListener
    private var mPageCount: Int? = null
    private var mCurrentPage: Int? = null
    private var mPageSize: Int? = null


    interface OnArticleInteractionListener{
        fun onArticleItemClicked(article: BlogArticle)
    }

    constructor(articles: MutableList<BlogArticle>, listener: OnArticleInteractionListener): super() {
        this.mArticles = articles
        this.mArticlesPage = articles
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BlogArticleViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return BlogArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.mArticlesPage.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_blog_article
    }

    fun setArticles(articles: MutableList<BlogArticle>, pageSize: Int) {
        this.mArticles = articles
        this.mPageCount = articles.size / pageSize
        this.mPageSize = pageSize
        if (articles.size % pageSize != 0) {
            mPageCount = mPageCount!! + 1
        }
        this.mCurrentPage = 1
        setPage(this.mCurrentPage!!)
    }
    private fun setPage(pageNumber:Int){
        if (pageNumber <= this.mPageCount!!) {
            val minIndex = (pageNumber - 1) * this.mPageSize!!
            var maxIndex = 0
            if ((minIndex + (this.mPageSize!! - 1)) > this.mArticles.size) {
                maxIndex = this.mArticles.size
            } else {
                maxIndex = minIndex +  this.mPageSize!!
            }
            this.mArticlesPage = this.mArticles.subList(minIndex, maxIndex)
        }

    }
    fun nextPage() {
        if ((this.mCurrentPage!! + 1) <= this.mPageCount!!) {
            this.mCurrentPage = this.mCurrentPage!! + 1
            this.setPage(this.mCurrentPage!!)
        }
    }
    fun previousPage() {
        if ((this.mCurrentPage!! - 1) >= 1) {
            this.mCurrentPage = this.mCurrentPage!! - 1
            this.setPage(this.mCurrentPage!!)
        }
    }
    fun getCurrentPage(): Int {
        return this.mCurrentPage!!
    }
    fun getPageCount(): Int {
        return this.mPageCount!!
    }
    override fun onBindViewHolder(holder: BlogArticleViewHolder?, position: Int) {
        (holder as BlogArticleViewHolder).bindData( this.mArticlesPage[position], this.mListener)
    }

    fun getPageSize(): Int {
        return this.mPageSize!!
    }
}