package com.estacionvital.patienthub.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.ArticleCategory
import com.estacionvital.patienthub.model.EVUserProfile
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.IMainDrawerPresenter
import com.estacionvital.patienthub.presenter.implementations.MainDrawerPresenterImpl
import com.estacionvital.patienthub.ui.fragments.ArticleCategoryFragment
import com.estacionvital.patienthub.ui.fragments.ProfileFragment
import com.estacionvital.patienthub.ui.views.IMainDrawerView
import com.estacionvital.patienthub.util.toast
import kotlinx.android.synthetic.main.activity_main_drawer.*
import kotlinx.android.synthetic.main.app_bar_main_activity_drawer.*

class MainActivityDrawer : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener, IMainDrawerView{
    override fun onLoadingProfile() {
        this.showProgressDialog(getString(R.string.profile_fetch_progress))
    }

    override fun onProfileLoadingFinished() {
        this.hideProgressDialog()
        this.mMainDrawerPresenter.retrieveLocalUserProfileSession()
    }

    private lateinit var mTextName: TextView
    private lateinit var mTextPhone: TextView

    private lateinit var navigationView: NavigationView
    private lateinit var mMainDrawerPresenter: IMainDrawerPresenter
    private lateinit var mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)
        setSupportActionBar(toolbar)
        navigationView = findViewById(R.id.nav_view)
        var headerView = navigationView.getHeaderView(0)

        mTextName = headerView.findViewById(R.id.text_name)
        mTextPhone = headerView.findViewById(R.id.text_phone)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        mEstacionVitalRemoteDataSource = EstacionVitalRemoteDataSource.INSTANCE

        mMainDrawerPresenter = MainDrawerPresenterImpl(this, mEstacionVitalRemoteDataSource)
        mMainDrawerPresenter.retrieveEVUSerProfile()

        fragmentTransaction(ProfileFragment())

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        loadFragment(id)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }



    private fun fragmentTransaction(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragments, fragment)
                .commit()
    }
    private fun loadFragment(menuId: Int){
        var fragment: Fragment = Fragment()
        when(menuId){
            R.id.nav_home -> {

            }
            R.id.nav_profile -> fragment = ProfileFragment()
            R.id.nav_chat_free -> {

            }
            R.id.nav_chat_premium -> {

            }
            R.id.nav_documents -> {

            }
            R.id.nav_articles -> {
                val activity = this
                fragment = ArticleCategoryFragment.newInstance(object: ArticleCategoryFragment
                .OnArticleCategoryInteraction{
                    override fun onLoadingCategories() {
                        activity.showProgressDialog(getString(R.string.article_categories_progress))
                    }

                    override fun onCategoriesLoaded() {
                        activity.hideProgressDialog()
                    }

                    override fun onError(msg: String) {
                        activity.toast(msg)
                    }

                    override fun showCustomMessage(msg: String) {
                        activity.toast(msg)
                    }

                    override fun onCategorySelected(category: ArticleCategory) {
                        navigateToArticleSelection(category)
                    }

                })
            }
        }
        fragmentTransaction(fragment)
    }

    private fun navigateToArticleSelection(category: ArticleCategory){
        val targetIntent = Intent(this, ArticleSelectionActivity::class.java)
        targetIntent.putExtra("articleCategory", category)
        startActivity(targetIntent)
    }


    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun setDrawerHeaderData(data: EVUserProfile) {

        mTextName.text = "${EVUserSession.instance.userProfile.name} ${EVUserSession.instance.userProfile.last_name}"
        mTextPhone.text = "${EVUserSession.instance.phoneNumber}"
        //Toast.makeText(this, "${data.name}", Toast.LENGTH_LONG)
    }
}
