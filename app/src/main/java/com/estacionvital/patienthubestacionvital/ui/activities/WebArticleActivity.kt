package com.estacionvital.patienthubestacionvital.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.webkit.WebView
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.model.BlogArticle
import android.content.Intent


class WebArticleActivity : AppCompatActivity() {

    private lateinit var mBlogWebView: WebView
    private lateinit var mShareFab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_article)
        val article: BlogArticle
        mBlogWebView = findViewById(R.id.web_blog)
        mShareFab = findViewById(R.id.fab_share_article)
        mBlogWebView.settings.javaScriptEnabled = true
        if (intent.extras != null) {
            article = intent.extras.getParcelable("article")
            supportActionBar!!.title = article.title
            mBlogWebView.loadUrl(article.url)

            mShareFab.setOnClickListener {
                shareArticle(article.title,
                        article.url)
            }
        }

    }
    private fun shareArticle(subject:String, extraText: String) {
        val share = Intent(android.content.Intent.ACTION_SEND)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)

        share.putExtra(Intent.EXTRA_SUBJECT, subject)
        share.putExtra(Intent.EXTRA_TEXT, extraText)

        startActivity(Intent.createChooser(share, getString(R.string.share_msg)))
    }
}
