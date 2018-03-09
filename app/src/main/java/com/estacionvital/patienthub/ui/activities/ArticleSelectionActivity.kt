package com.estacionvital.patienthub.ui.activities

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthub.model.ArticleCategory
import com.estacionvital.patienthub.model.BlogArticle
import com.estacionvital.patienthub.presenter.IArticleSelectionPresenter
import com.estacionvital.patienthub.presenter.implementations.ArticleSelectionPresenterImpl
import com.estacionvital.patienthub.ui.adapters.BlogArticleAdapter
import com.estacionvital.patienthub.ui.views.IArticleSelectionView
import com.estacionvital.patienthub.util.toast

class ArticleSelectionActivity : BaseActivity(), IArticleSelectionView,
    BlogArticleAdapter.OnArticleInteractionListener{
    override fun onArticleItemClicked(article: BlogArticle) {
        navigateToWebArticle(article)
    }

    private lateinit var mArticlesRecyclerView: RecyclerView
    private lateinit var mArticleSelectionPresenter: IArticleSelectionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_selection)
        var category: ArticleCategory
        mArticleSelectionPresenter = ArticleSelectionPresenterImpl(this, EVBlogRemoteDataSource.INSTANCE)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mArticlesRecyclerView = findViewById(R.id.recycler_articles)
        mArticlesRecyclerView.setHasFixedSize(true)
        mArticlesRecyclerView.layoutManager = LinearLayoutManager(this)
        mArticlesRecyclerView.adapter = BlogArticleAdapter(ArrayList<BlogArticle>(), this)

        if (intent.extras != null) {
            category = intent.extras.getParcelable("articleCategory")
            supportActionBar!!.title = String.format(getString(R.string.title_activity_article_selection),
                    category.title)
            mArticleSelectionPresenter.loadArticles(category.id)
            //this.toast(category.title)
        }
    }

    override fun showArticlesLoadingProgress() {
        this.showProgressDialog(getString(R.string.blog_article_progress))
    }

    override fun hideArticlesLoadingProgress() {
        this.hideProgressDialog()
    }

    override fun navigateToWebArticle(article: BlogArticle) {

    }

    override fun updateArticlesListUI(articles: MutableList<BlogArticle>) {
        (mArticlesRecyclerView.adapter as BlogArticleAdapter).setArticles(articles)
        (mArticlesRecyclerView.adapter as BlogArticleAdapter).notifyDataSetChanged()
    }

    override fun showInternalServerError() {
        this.toast(R.string.generic_500_error)
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
