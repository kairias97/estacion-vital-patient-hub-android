package com.estacionvital.patienthub.ui.activities

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVUserProfile
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.implementations.MainDrawerPresenterImpl
import com.estacionvital.patienthub.presenter.implementations.ProfilePresenterImpl
import com.estacionvital.patienthub.ui.fragmentViews.IProfileFragmentView
import com.estacionvital.patienthub.ui.fragments.ProfileFragment
import com.estacionvital.patienthub.ui.views.IMainDrawerView
import kotlinx.android.synthetic.main.activity_main_drawer.*
import kotlinx.android.synthetic.main.app_bar_main_activity_drawer.*

class MainActivityDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ProfileFragment.OnFragmentInteractionListener, IMainDrawerView{

    private lateinit var mtextName: TextView
    private lateinit var mtextPhone: TextView

    private lateinit var navigationView: NavigationView
    private lateinit var mMainDrawerPresenterImpl: MainDrawerPresenterImpl
    private lateinit var mEstacionVitalRemoteDataSource: EstacionVitalRemoteDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)
        setSupportActionBar(toolbar)
        navigationView = findViewById(R.id.nav_view)
        var headerView = navigationView.getHeaderView(0)

        mtextName = headerView.findViewById(R.id.text_name)
        mtextPhone = headerView.findViewById(R.id.text_phone)


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

        mMainDrawerPresenterImpl = MainDrawerPresenterImpl(this, mEstacionVitalRemoteDataSource)
        mMainDrawerPresenterImpl.retrieveEVUSerProfile()

        fragmentTransaction(ProfileFragment())

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
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
        var fragment: Fragment = ProfileFragment()
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

            }
        }
        fragmentTransaction(fragment)
    }

    override fun onFragmentInteraction(uri: Uri) {
        //a implementar
    }

    override fun showLoadingProgress() {

    }

    override fun hideLoadingProgress() {

    }

    override fun showError() {

    }

    override fun retrieveData(data: EVUserProfile) {
        EVUserSession.instance.userProfile = data

        mtextName.text = "${EVUserSession.instance.userProfile.name} ${EVUserSession.instance.userProfile.last_name}"
        mtextPhone.text = "${EVUserSession.instance.phoneNumber}"
        Toast.makeText(this, "${data.name}", Toast.LENGTH_LONG)
    }
}
