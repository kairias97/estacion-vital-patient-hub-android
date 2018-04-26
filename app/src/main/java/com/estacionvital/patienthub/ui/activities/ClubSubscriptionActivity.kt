package com.estacionvital.patienthub.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.EVClub
import com.estacionvital.patienthub.model.RegistrationSession
import com.estacionvital.patienthub.presenter.ISuscriptionCatalogPresenter
import com.estacionvital.patienthub.presenter.implementations.ClubSubscriptionPresenterImpl
import com.estacionvital.patienthub.ui.adapters.EVClubAdapter
import com.estacionvital.patienthub.ui.views.IClubSubscriptionView
import com.estacionvital.patienthub.util.NETMOBILE_AUTH_CREDENTIAL
import com.estacionvital.patienthub.util.toast

class ClubSubscriptionActivity : BaseActivity(), IClubSubscriptionView, EVClubAdapter.OnClubSelectedListener{


    override fun navigateToMain() {
        val mainNavigationIntent: Intent = Intent(this, MainActivityDrawer::class.java)
        mainNavigationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainNavigationIntent)
    }

    override fun showActiveSubscriptionsProgress() {
        showProgressDialog(getString(R.string.active_subscriptions_progress))
    }

    override fun hideActiveSubscriptionsProgress() {
        hideProgressDialog()
    }

    override fun showCustomWSMessage(msg: String) {
        this.toast(getString(R.string.custom_ws_message).format(msg))
    }

    override fun showNewClubSuccessMessage(clubName: String) {
        this.toast(getString(R.string.success_new_club_msg).format(clubName))
    }

    override fun showSubscriptionSuccessMessage() {
        this.toast(R.string.success_club_subscription_msg)
    }

    override fun hideSubscriptionProgress() {
        hideProgressDialog()
    }

    override fun showSubscriptionProgress(clubName: String) {
        val msg: String = getString(R.string.subscription_club_progress_msg).format(clubName)
        showProgressDialog(msg)
    }

    override fun navigateToEVRegistration() {
        val registrationIntent = Intent(this, RegistrationProfileActivity::class.java)
        registrationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(registrationIntent)
    }

    override fun showSubscriptionsRequiredMessage() {
        this.toast(R.string.validation_suscription_required_msg)
    }

    override fun getSelectedClubs(): List<EVClub> {
        return (mSuscriptionRecyclerView.adapter as EVClubAdapter)!!.getSelectedClubs()
    }

    override fun hideRetrievingCatalogProcess() {
        hideProgressDialog()
    }
    override fun getNewSelectedClubsCount(): Int {
        return (mSuscriptionRecyclerView.adapter as? EVClubAdapter)!!.getNewSelectedClubsCount()
    }
    override fun getSelectedClubsCount(): Int {
        return (mSuscriptionRecyclerView.adapter as? EVClubAdapter)!!.getSelectedClubsCount()
    }


    public lateinit var mClubAdapter: EVClubAdapter
    public lateinit var mSuscriptionRecyclerView: RecyclerView
    lateinit var mCancelButton: Button
    lateinit var mAcceptButton: Button

    private lateinit var mCatalogSuscriptionPresenter: ISuscriptionCatalogPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_suscription)

        var isLoggedInUser = false
        if (intent.extras != null && intent.hasExtra("isLoggedIn")) {
            isLoggedInUser = intent.extras.getBoolean("isLoggedIn", false)
        }
        mCancelButton = findViewById(R.id.button_cancel)
        mAcceptButton = findViewById(R.id.button_accept)

        mSuscriptionRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        mSuscriptionRecyclerView.layoutManager = LinearLayoutManager(this)
        mSuscriptionRecyclerView.setHasFixedSize(true)

        mCatalogSuscriptionPresenter = ClubSubscriptionPresenterImpl(this, NetMobileRemoteDataSource.INSTANCE)
        mCatalogSuscriptionPresenter.retrieveLimit(isLoggedInUser, NETMOBILE_AUTH_CREDENTIAL)

        mClubAdapter = EVClubAdapter(ArrayList<EVClub>(), this)
        mSuscriptionRecyclerView.adapter = mClubAdapter

        mCancelButton.setOnClickListener(){
            finish()
        }
        mAcceptButton.setOnClickListener(){
            mCatalogSuscriptionPresenter.validateSubscriptions(isLoggedInUser)
        }
    }

    override fun showInternalErrorMessage() {

    }

    override fun showLimitSubscriptionMessage() {
        this.toast(R.string.validation_suscriptions_limit_msg)
    }

    override fun showRetrievingCatalogProcess() {
        showProgressDialog(getString(R.string.club_catalog_process))
    }

    override fun showClubSubscription(clubs: MutableList<EVClub>) {
        (mSuscriptionRecyclerView.adapter as? EVClubAdapter)!!.setClubsList(clubs)
        (mSuscriptionRecyclerView.adapter as EVClubAdapter)!!.notifyDataSetChanged()
    }

    override fun updateClubSubscriptionView(club: EVClub) {
        (mSuscriptionRecyclerView.adapter as? EVClubAdapter)!!.setClub(club)
        (mSuscriptionRecyclerView.adapter as EVClubAdapter)!!.notifyDataSetChanged()
    }
    //For item of mClubAdapter
    override fun onClubItemClicked(club: EVClub) {
        mCatalogSuscriptionPresenter.validateSelectedClub(club)
    }

}
