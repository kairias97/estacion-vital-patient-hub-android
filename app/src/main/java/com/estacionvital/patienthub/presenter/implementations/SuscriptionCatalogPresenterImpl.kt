package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionCatalogCallback
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.SuscriptionCatalogRequest
import com.estacionvital.patienthub.model.SuscriptionCatalogResponse
import com.estacionvital.patienthub.presenter.ISuscriptionCatalogPresenter
import com.estacionvital.patienthub.ui.views.IClubSuscriptionView
import com.estacionvital.patienthub.util.AUTH_CREDENTIAL
import javax.security.auth.callback.Callback

/**
 * Created by dusti on 26/02/2018.
 */
class SuscriptionCatalogPresenterImpl: ISuscriptionCatalogPresenter {

    private val mSuscriptionCatalogView: IClubSuscriptionView
    private val mNetMobileRemoteDataSource: NetMobileRemoteDataSource

    constructor(clubSuscriptionView: IClubSuscriptionView, netMobileRemoteDataSource: NetMobileRemoteDataSource){
        this.mSuscriptionCatalogView = clubSuscriptionView
        this.mNetMobileRemoteDataSource = netMobileRemoteDataSource
    }

    override fun retrieveCatalog(auth_credential: String) {
        mNetMobileRemoteDataSource.retrieveSuscriptionCatalog(SuscriptionCatalogRequest(AUTH_CREDENTIAL),
                object: ISuscriptionCatalogCallback{
                    override fun onSucces(response: List<SuscriptionCatalogResponse>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onFailure() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }
}