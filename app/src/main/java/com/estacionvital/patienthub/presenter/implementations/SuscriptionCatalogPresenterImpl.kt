package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionActiveCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionCatalogCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionLimitCallback
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.*
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
        mSuscriptionCatalogView.showRetrievingProcces()
        mNetMobileRemoteDataSource.retrieveSuscriptionCatalog(SuscriptionCatalogRequest(auth_credential),
                object: ISuscriptionCatalogCallback{
                    override fun onSucces(response: List<SuscriptionCatalogResponse>) {
                        //se copia la lista a nuestra lista local
                        print(response.count() as Any)
                    }

                    override fun onFailure() {
                        //en caso de error
                    }
                })
    }

    override fun retrieveLimit(auth_credential: String) {
        mNetMobileRemoteDataSource.retrieveSuscriptionLimit(SuscriptionLimitRequest(auth_credential),
                object: ISuscriptionLimitCallback{
                    override fun onSuccess(response: SuscriptionLimitResponse) {
                        //se obtiene el limite
                    }

                    override fun onFailure() {
                        //en caso de error
                    }
                })
    }

    override fun retrieveActive(number: String, auth_credential: String, catalog: List<SuscriptionCatalogResponse>) {
        mNetMobileRemoteDataSource.retrieveSucriptionActive(SuscriptionActiveRequest(number, auth_credential),
                object: ISuscriptionActiveCallback{
                    override fun onSuccess(response: SuscriptionActiveResponse) {
                        //se recibe lista de suscripciones activas
                    }

                    override fun onFailure() {
                        //en caso de error
                    }
                })
    }
}