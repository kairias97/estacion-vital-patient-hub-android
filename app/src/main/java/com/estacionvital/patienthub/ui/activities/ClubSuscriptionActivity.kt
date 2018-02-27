package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.EVClub
import com.estacionvital.patienthub.model.RegistrationSession
import com.estacionvital.patienthub.presenter.ISuscriptionCatalogPresenter
import com.estacionvital.patienthub.presenter.implementations.SuscriptionCatalogPresenterImpl
import com.estacionvital.patienthub.ui.Adapters.EVClubAdapter
import com.estacionvital.patienthub.ui.views.IClubSuscriptionView
import com.estacionvital.patienthub.util.AUTH_CREDENTIAL

class ClubSuscriptionActivity : BaseActivity(), IClubSuscriptionView {
    public lateinit var adapter: EVClubAdapter
    public lateinit var recyclerView: RecyclerView
    public var listaVacia: List<EVClub> = ArrayList<EVClub>()

    private lateinit var mCatalogSuscriptionPresenter: ISuscriptionCatalogPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_suscription)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        mCatalogSuscriptionPresenter = SuscriptionCatalogPresenterImpl(this, NetMobileRemoteDataSource.INSTANCE)
        mCatalogSuscriptionPresenter.retrieveLimit(RegistrationSession.instance.phoneNumber, AUTH_CREDENTIAL)

        adapter = EVClubAdapter(listaVacia)
        recyclerView.adapter = adapter
    }

    override fun showInternalErrorMessage() {

    }

    override fun showLimitSuscriptionMessage() {

    }

    override fun showRetrievingProcces() {

    }

    override fun showClubSuscription(clubs: List<EVClub>) {
        (recyclerView.adapter as? EVClubAdapter)!!.setClubsList(clubs)
        (recyclerView.adapter as EVClubAdapter)!!.notifyDataSetChanged()
    }
}
