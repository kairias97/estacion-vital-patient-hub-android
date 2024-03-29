package com.estacionvital.patienthub.ui.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.local.SharedPrefManager
import com.estacionvital.patienthub.data.remote.EVTwilioChatRemoteDataSource
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.IMainDrawerPresenter
import com.estacionvital.patienthub.presenter.implementations.MainDrawerPresenterImpl
import com.estacionvital.patienthub.services.RegistrationIntentService
import com.estacionvital.patienthub.ui.fragments.*
import com.estacionvital.patienthub.ui.views.IMainDrawerView
import com.estacionvital.patienthub.util.*
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main_drawer.*
import kotlinx.android.synthetic.main.app_bar_main_activity_drawer.*

class MainActivityDrawer : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener, IMainDrawerView{
    override fun onProfileLoadedSuccessfully(data: EVUserProfile) {
        this.setDrawerHeaderData(data)
        if(!EVChatSession.instance.isChatClientCreated){
            mMainDrawerPresenter.getTwilioToken(this)
        }
    }

    override fun navigateToNumberVerification() {
        val exitIntent = Intent(this, NumberVerificationActivity::class.java)
        exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(exitIntent)
    }

    override fun showLoggingOutProgress() {
        showProgressDialog(getString(R.string.logout_progress))
    }

    override fun hideLoggingOutProgress() {
        this.hideProgressDialog()
    }

    override fun onLoadingProfile() {
        this.showProgressDialog(getString(R.string.profile_fetch_progress))
    }

    override fun onProfileLoadingFinished() {
        this.hideProgressDialog()
    }

    override fun showCreatingClientProgress() {
        this.showProgressDialog("")
    }

    override fun chatClientFinished() {
        val intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent)
    }

    private lateinit var mTextName: TextView
    private lateinit var mTextPhone: TextView

    private lateinit var navigationView: NavigationView
    private lateinit var mMainDrawerPresenter: IMainDrawerPresenter
    private lateinit var mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    private lateinit var mFabButtonChatFree: FloatingActionButton
    private lateinit var mFabButtonChatPremium: FloatingActionButton
    private var goToDocuments: Boolean = false

    private lateinit var mTypeChat: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)
        setSupportActionBar(toolbar)
        navigationView = findViewById(R.id.nav_view)
        var headerView = navigationView.getHeaderView(0)

        mTextName = headerView.findViewById(R.id.text_name)
        mTextPhone = headerView.findViewById(R.id.text_phone)

        mFabButtonChatFree = findViewById<FloatingActionButton>(R.id.fab_chat_free)
        mFabButtonChatPremium = findViewById<FloatingActionButton>(R.id.fab_chat_premium)

        fab.isIconAnimated = false

        fab.setOnClickListener { view ->

        }
        mFabButtonChatFree.setOnClickListener {
            fab.close(true)
            mTypeChat = CHAT_FREE
            fragmentTransaction(ConversationHistoryFragment.newInstance(CHAT_FREE, object: ConversationHistoryFragment
            .OnConversationHistorytInteraction{
                override fun onConversationSelected(channel: EVChannel) {
                    navigateToChat(channel)
                }

                override fun onHistoryLoadingFinished() {
                    hideProgressDialog()
                }

                override fun onLoadingHistory() {
                    showProgressDialog(getString(R.string.chat_getting_conversations_history))
                }

                override fun navigateToSpecialty() {
                    navigateToSpecialtySelection()
                }

                override fun onHistoryLoadingError() {
                   toast(getString(R.string.generic_500_error))
                }
            }))
            navigationView.setCheckedItem(R.id.nav_chat_free)
        }
        mFabButtonChatPremium.setOnClickListener {
            fab.close(true)
            mTypeChat = CHAT_PREMIUM
            fragmentTransaction(ConversationHistoryFragment.newInstance(CHAT_PREMIUM, object: ConversationHistoryFragment
            .OnConversationHistorytInteraction{
                override fun onConversationSelected(channel: EVChannel) {
                    navigateToChat(channel)
                }

                override fun onHistoryLoadingFinished() {
                    hideProgressDialog()
                }

                override fun onLoadingHistory() {
                    showProgressDialog(getString(R.string.chat_getting_conversations_history))
                }

                override fun navigateToSpecialty() {
                    navigateToSpecialtySelection()
                }

                override fun onHistoryLoadingError() {
                    toast(getString(R.string.generic_500_error))
                }
            }))
            navigationView.setCheckedItem(R.id.nav_chat_premium)
        }


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        //This comes from the xml
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        drawer_layout.addDrawerListener(object:DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
                toggle.onDrawerStateChanged(newState)
            }

            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                toggle.onDrawerSlide(drawerView, slideOffset)
            }

            override fun onDrawerClosed(drawerView: View?) {
                toggle.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View?) {
                if (fab.isOpened) {
                    fab.close(true)
                }
                toggle.onDrawerOpened(drawerView)
            }

        })
        nav_view.setNavigationItemSelectedListener(this)

        navigationView.setCheckedItem(R.id.nav_home)


        fragmentTransaction(HomeFragment.newInstance(object: HomeFragment.HomeFragmentListener {
            override fun onRecentArticlesBannerSelected() {
                navigateToRecentArticles()
            }

        }))

        mTypeChat = ""

        if(intent.extras != null){
            goToDocuments = intent.extras.getBoolean("goToDocuments")
            if(goToDocuments){
                val activity = this
                fragmentTransaction(DocumentHistoryFragment.newInstance(object: DocumentHistoryFragment.DocumentHistoryFragmentListener{
                    override fun onDocumentSelected(document: Document) {

                        val url = "$EV_MAIN_DOCS_URL${document.url}?token=${Uri.encode(EVUserSession.instance.authToken)}"
                        //activity.toast("Documento seleccionado: ${document.specialty}")
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }

                    override fun onDocumentLoadingStarted() {
                        activity.showProgressDialog(getString(R.string.document_progress))
                    }

                    override fun onDocumentLoadingFinished() {
                        activity.hideProgressDialog()
                    }

                    override fun onDocumentLoadingError() {
                        activity.toast(R.string.document_fetch_error)
                    }
                }))
            }
        }
        mEstacionVitalRemoteDataSource = EstacionVitalRemoteDataSource.INSTANCE

        mMainDrawerPresenter = MainDrawerPresenterImpl(this, mEstacionVitalRemoteDataSource,
                SharedPrefManager(
                        getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                                Context.MODE_PRIVATE)
                ), EVTwilioChatRemoteDataSource.instance)
        mMainDrawerPresenter.retrieveEVUSerProfile(this)

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
                fragment = HomeFragment.newInstance(object: HomeFragment.HomeFragmentListener {
                    override fun onRecentArticlesBannerSelected() {
                        navigateToRecentArticles()
                    }

                })
            }
            R.id.nav_profile -> fragment = ProfileFragment()
            R.id.nav_chat_free -> {
                val activity = this
                mTypeChat = CHAT_FREE
                fragment = ConversationHistoryFragment.newInstance(CHAT_FREE, object: ConversationHistoryFragment
                .OnConversationHistorytInteraction{
                    override fun onConversationSelected(channel: EVChannel) {
                        navigateToChat(channel)
                    }

                    override fun onHistoryLoadingFinished() {
                        activity.hideProgressDialog()
                    }

                    override fun onLoadingHistory() {
                        activity.showProgressDialog(getString(R.string.chat_getting_conversations_history))
                    }

                    override fun navigateToSpecialty() {
                        navigateToSpecialtySelection()
                    }

                    override fun onHistoryLoadingError() {
                        activity.toast(getString(R.string.generic_500_error))
                    }
                })
            }
            R.id.nav_chat_premium -> {
                val activity = this
                mTypeChat = CHAT_PREMIUM
                fragment = ConversationHistoryFragment.newInstance(CHAT_PREMIUM, object: ConversationHistoryFragment
                .OnConversationHistorytInteraction{
                    override fun onConversationSelected(channel: EVChannel) {
                        activity.navigateToChat(channel)
                    }

                    override fun onHistoryLoadingFinished() {
                        activity.hideProgressDialog()
                    }

                    override fun onLoadingHistory() {
                        activity.showProgressDialog(getString(R.string.chat_getting_conversations_history))
                    }

                    override fun navigateToSpecialty() {
                        navigateToSpecialtySelection()
                    }

                    override fun onHistoryLoadingError() {
                        activity.toast(getString(R.string.generic_500_error))
                    }
                })
            }
            R.id.nav_documents -> {
                val activity = this
                fragment = DocumentHistoryFragment.newInstance(object: DocumentHistoryFragment.DocumentHistoryFragmentListener {
                    override fun onDocumentSelected(document: Document) {

                        val url = "$EV_MAIN_DOCS_URL${document.url}?token=${Uri.encode(EVUserSession.instance.authToken)}"
                        //activity.toast("Documento seleccionado: ${document.specialty}")
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }

                    override fun onDocumentLoadingStarted() {
                        activity.showProgressDialog(getString(R.string.document_progress))
                    }

                    override fun onDocumentLoadingFinished() {
                        activity.hideProgressDialog()
                    }

                    override fun onDocumentLoadingError() {
                       activity.toast(R.string.document_fetch_error)
                    }

                })
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
            R.id.nav_logout -> {
                askForLogoutConfirmation()
                return
            }
        }
        fragmentTransaction(fragment)
    }

    private fun navigateToRecentArticles() {
        val recentArticlesIntent = Intent(this, RecentArticlesActivity::class.java)
        startActivity(recentArticlesIntent)
    }

    private fun navigateToChat(channel: EVChannel) {
        val targetIntent: Intent = Intent(this, TwilioChatActivity::class.java)
        targetIntent.putExtra("chatType",mTypeChat)
        targetIntent.putExtra("room_id", channel.unique_name)
        targetIntent.putExtra("specialty", channel.specialty)
        targetIntent.putExtra("isFinished", channel.isFinished)
        targetIntent.putExtra("channel", channel)
        startActivity(targetIntent)

    }

    override fun onResume() {
        super.onResume()
        if(intent.extras != null){
            goToDocuments = intent.extras.getBoolean("goToDocuments")
            if(goToDocuments){
                val activity = this
                fragmentTransaction(DocumentHistoryFragment.newInstance(object: DocumentHistoryFragment.DocumentHistoryFragmentListener{
                    override fun onDocumentSelected(document: Document) {

                        val url = "$EV_MAIN_DOCS_URL${document.url}?token=${Uri.encode(EVUserSession.instance.authToken)}"
                        //activity.toast("Documento seleccionado: ${document.specialty}")
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }

                    override fun onDocumentLoadingStarted() {
                        activity.showProgressDialog(getString(R.string.document_progress))
                    }

                    override fun onDocumentLoadingFinished() {
                        activity.hideProgressDialog()
                    }

                    override fun onDocumentLoadingError() {
                        activity.toast(R.string.document_fetch_error)
                    }
                }))
            }
        }
    }

    private fun askForLogoutConfirmation(){
        this.showConfirmDialog(titleResId = R.string.dialog_title_logout,
                iconResId = R.drawable.ic_logout_black_24dp,
                messageResId = R.string.dialog_msg_logout,
                positiveBtnResId = R.string.dialog_yes_logout,
                negativeBtnResId = R.string.dialog_cancel,
                negativeListener = object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        uncheckNavOption(R.id.nav_logout)
                    }
                },
                positiveListener = object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        mMainDrawerPresenter.logout()
                    }

                }
                )
    }
    private fun uncheckNavOption(menuResId: Int){
        navigationView.menu.findItem(menuResId).isChecked = false

    }
    private fun navigateToArticleSelection(category: ArticleCategory){
        val targetIntent = Intent(this, ArticleSelectionActivity::class.java)
        targetIntent.putExtra("articleCategory", category)
        startActivity(targetIntent)
    }

    private fun navigateToSpecialtySelection(){
        val targetIntent = Intent(this, SpecialtySelectionActivity::class.java)
        targetIntent.putExtra("chatType", mTypeChat)
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
