package com.estacionvital.patienthubestacionvital.ui.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.TextView
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.data.local.SharedPrefManager
import com.estacionvital.patienthubestacionvital.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.ArticleCategory
import com.estacionvital.patienthubestacionvital.model.Document
import com.estacionvital.patienthubestacionvital.model.EVUserProfile
import com.estacionvital.patienthubestacionvital.model.EVUserSession
import com.estacionvital.patienthubestacionvital.presenter.IMainDrawerPresenter
import com.estacionvital.patienthubestacionvital.presenter.implementations.MainDrawerPresenterImpl
import com.estacionvital.patienthubestacionvital.ui.fragments.ArticleCategoryFragment
import com.estacionvital.patienthubestacionvital.ui.fragments.ConversationHistoryFragment
import com.estacionvital.patienthubestacionvital.ui.fragments.DocumentHistoryFragment
import com.estacionvital.patienthubestacionvital.ui.fragments.ProfileFragment
import com.estacionvital.patienthubestacionvital.ui.views.IMainDrawerView
import com.estacionvital.patienthubestacionvital.util.CHAT_FREE
import com.estacionvital.patienthubestacionvital.util.CHAT_PREMIUM
import com.estacionvital.patienthubestacionvital.util.toast
import kotlinx.android.synthetic.main.activity_main_drawer.*
import kotlinx.android.synthetic.main.app_bar_main_activity_drawer.*

class MainActivityDrawer : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener, IMainDrawerView{
    override fun onProfileLoadedSuccessfully(data: EVUserProfile) {
        this.setDrawerHeaderData(data)
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
        hideProgressDialog()
    }

    override fun onLoadingProfile() {
        this.showProgressDialog(getString(R.string.profile_fetch_progress))
    }

    override fun onProfileLoadingFinished() {
        this.hideProgressDialog()

    }

    private lateinit var mTextName: TextView
    private lateinit var mTextPhone: TextView

    private lateinit var navigationView: NavigationView
    private lateinit var mMainDrawerPresenter: IMainDrawerPresenter
    private lateinit var mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    private lateinit var mTypeChat: String

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

        mMainDrawerPresenter = MainDrawerPresenterImpl(this, mEstacionVitalRemoteDataSource,
                SharedPrefManager(
                        getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                                Context.MODE_PRIVATE)
                ))
        mMainDrawerPresenter.retrieveEVUSerProfile()

        fragmentTransaction(ProfileFragment())

        mTypeChat = ""

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
                this.supportActionBar?.title = getString(R.string.drawer_home)
            }
            R.id.nav_profile -> fragment = ProfileFragment()
            R.id.nav_chat_free -> {
                val activity = this
                mTypeChat = CHAT_FREE
                fragment = ConversationHistoryFragment.newInstance(CHAT_FREE, object: ConversationHistoryFragment
                .OnConversationHistorytInteraction{
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
                        activity.toast("Documento seleccionado: ${document.specialty}")
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
