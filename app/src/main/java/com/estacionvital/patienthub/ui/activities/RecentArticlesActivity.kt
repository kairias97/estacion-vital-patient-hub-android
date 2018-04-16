package com.estacionvital.patienthub.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthub.model.BlogArticle
import com.estacionvital.patienthub.presenter.IRecentArticlesPresenter
import com.estacionvital.patienthub.presenter.implementations.RecentArticlesPresenterImpl
import com.estacionvital.patienthub.ui.adapters.BlogArticleAdapter
import com.estacionvital.patienthub.ui.views.IRecentArticlesView
import com.estacionvital.patienthub.util.toast

class RecentArticlesActivity : BaseActivity(), IRecentArticlesView {
    override fun updateUIList(articles: MutableList<BlogArticle>, pageSize: Int) {
        (mArticlesRecyclerView.adapter as BlogArticleAdapter).setArticles(articles, pageSize)
        (mArticlesRecyclerView.adapter as BlogArticleAdapter).notifyDataSetChanged()
    }

    override fun showErrorMessage() {
        this.toast(R.string.generic_500_error)
    }

    override fun showLoadingProgress() {
        this.showProgressDialog(getString(R.string.blog_recent_articles_progress))
    }

    override fun hideLoadingProgress() {
        this.hideProgressDialog()
    }

    private lateinit var mArticlesRecyclerView: RecyclerView
    private lateinit var mArticleSelectionPresenter: IRecentArticlesPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_articles)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.title = getString(R.string.title_activity_recent_articles)
        mArticlesRecyclerView = findViewById(R.id.recycler_articles)
        mArticlesRecyclerView.setHasFixedSize(true)
        mArticlesRecyclerView.layoutManager = LinearLayoutManager(this)
        mArticlesRecyclerView.adapter = BlogArticleAdapter(ArrayList<BlogArticle>(), object: BlogArticleAdapter.OnArticleInteractionListener {
            override fun onArticleItemClicked(article: BlogArticle) {
                navigateToWebArticle(article)
            }

        })
        mArticleSelectionPresenter = RecentArticlesPresenterImpl(this, EVBlogRemoteDataSource.INSTANCE)
        mArticleSelectionPresenter.loadArticles()
    }
    private fun navigateToWebArticle(article: BlogArticle) {
        val webIntent = Intent(this, WebArticleActivity::class.java)
        webIntent.putExtra("article", article)
        startActivity(webIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                returnTop()
            }
        }
        return true
    }

    private fun returnTop(){
        NavUtils.navigateUpFromSameTask(this)
    }
}
