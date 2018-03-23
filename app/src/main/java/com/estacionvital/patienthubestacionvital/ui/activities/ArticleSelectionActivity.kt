package com.estacionvital.patienthubestacionvital.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.data.remote.EVBlogRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.ArticleCategory
import com.estacionvital.patienthubestacionvital.model.BlogArticle
import com.estacionvital.patienthubestacionvital.presenter.IArticleSelectionPresenter
import com.estacionvital.patienthubestacionvital.presenter.implementations.ArticleSelectionPresenterImpl
import com.estacionvital.patienthubestacionvital.ui.adapters.BlogArticleAdapter
import com.estacionvital.patienthubestacionvital.ui.views.IArticleSelectionView
import com.estacionvital.patienthubestacionvital.util.toast

class ArticleSelectionActivity : BaseActivity(), IArticleSelectionView,
    BlogArticleAdapter.OnArticleInteractionListener{
    override fun showPreviousPageButton() {
        if (mPreviousPageButton.visibility != View.VISIBLE) {
            mPreviousPageButton.visibility = View.VISIBLE
        }

    }

    override fun showNextPageButton() {
        if (mNextPageButton.visibility != View.VISIBLE) {
            mNextPageButton.visibility = View.VISIBLE
        }
    }

    override fun hidePreviousPageButton() {
        if (mPreviousPageButton.visibility != View.GONE) {
            mPreviousPageButton.visibility = View.GONE
        }
    }

    override fun hideNextPageButton() {
        if (mNextPageButton.visibility != View.GONE) {
            mNextPageButton.visibility = View.GONE
        }
    }

    override fun requestNextPage() {
        (mArticlesRecyclerView.adapter as BlogArticleAdapter).nextPage()
        (mArticlesRecyclerView.adapter as BlogArticleAdapter).notifyDataSetChanged()

    }

    override fun requestPreviousPage() {
        (mArticlesRecyclerView.adapter as BlogArticleAdapter).previousPage()
        (mArticlesRecyclerView.adapter as BlogArticleAdapter).notifyDataSetChanged()
    }

    override fun getCurrentArticlePage(): Int {
        return (mArticlesRecyclerView.adapter as BlogArticleAdapter).getCurrentPage()
    }

    override fun getMaxArticlePage(): Int {
        return (mArticlesRecyclerView.adapter as BlogArticleAdapter).getPageCount()
    }

    override fun getPageSize(): Int {
        return (mArticlesRecyclerView.adapter as BlogArticleAdapter).getPageSize()
    }

    override fun updatePagerIndicator(currentPage: Int, maxPage: Int) {
        mPagerIndicadorTextView.text = getString(R.string.placeholder_pager).format(currentPage,
                maxPage)
    }

    override fun onArticleItemClicked(article: BlogArticle) {
        navigateToWebArticle(article)
    }

    private lateinit var mArticlesRecyclerView: RecyclerView
    private lateinit var mPreviousPageButton: Button
    private lateinit var mNextPageButton: Button
    private lateinit var mPagerIndicadorTextView: TextView
    private lateinit var mArticleSelectionPresenter: IArticleSelectionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_selection)
        var category: ArticleCategory
        mArticleSelectionPresenter = ArticleSelectionPresenterImpl(this, EVBlogRemoteDataSource.INSTANCE)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mArticlesRecyclerView = findViewById(R.id.recycler_articles)
        mPagerIndicadorTextView = findViewById(R.id.text_pager_indicator)
        mPreviousPageButton = findViewById(R.id.button_previous)
        mNextPageButton = findViewById(R.id.button_next)

        mPreviousPageButton.setOnClickListener {
            mArticleSelectionPresenter.onPreviousPageRequested()
        }

        mNextPageButton.setOnClickListener{
            mArticleSelectionPresenter.onNextPageRequested()
        }
        mArticlesRecyclerView.setHasFixedSize(true)
        mArticlesRecyclerView.layoutManager = LinearLayoutManager(this)
        mArticlesRecyclerView.adapter = BlogArticleAdapter(ArrayList<BlogArticle>(), this)
        mPagerIndicadorTextView.text = getString(R.string.placeholder_pager).format(0,0)
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
        val webIntent: Intent = Intent(this, WebArticleActivity::class.java)
        webIntent.putExtra("article", article)
        startActivity(webIntent)
    }

    override fun updateArticlesListUI(articles: MutableList<BlogArticle>, pageSize: Int) {
        (mArticlesRecyclerView.adapter as BlogArticleAdapter).setArticles(articles, pageSize)
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
