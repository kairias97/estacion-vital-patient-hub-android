package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.ArticleCategory
import com.estacionvital.patienthub.util.toast

class ArticleSelectionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_selection)
        var category: ArticleCategory
        if (intent.extras != null) {
            category = intent.extras.getParcelable("articleCategory")
            supportActionBar!!.title = String.format(getString(R.string.title_activity_article_selection),
                    category.title)
            this.toast(category.title)
        }
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
