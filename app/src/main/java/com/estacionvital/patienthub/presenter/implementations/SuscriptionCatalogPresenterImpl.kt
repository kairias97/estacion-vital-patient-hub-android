package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionActiveCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionCatalogCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionLimitCallback
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.ISuscriptionCatalogPresenter
import com.estacionvital.patienthub.ui.views.IClubSuscriptionView

/**
 * Created by dusti on 26/02/2018.
 */
class SuscriptionCatalogPresenterImpl: ISuscriptionCatalogPresenter {

    private val mSuscriptionCatalogView: IClubSuscriptionView
    private val mNetMobileRemoteDataSource: NetMobileRemoteDataSource
    private lateinit var suscriptionLimit: SuscriptionLimitResponse
    private lateinit var suscriptionCatalog: List<EVClub>

    constructor(clubSuscriptionView: IClubSuscriptionView, netMobileRemoteDataSource: NetMobileRemoteDataSource){
        this.mSuscriptionCatalogView = clubSuscriptionView
        this.mNetMobileRemoteDataSource = netMobileRemoteDataSource
    }

    override fun retrieveCatalog(number: String, auth_credential: String) {
        mSuscriptionCatalogView.showRetrievingProcces()
        mNetMobileRemoteDataSource.retrieveSuscriptionCatalog(SuscriptionCatalogRequest(auth_credential),
                object: ISuscriptionCatalogCallback{
                    override fun onSucces(response: List<EVClub>) {
                        if(response.count()>0){
                            retrieveActive(number, auth_credential, response)
                        }
                        else{
                            mSuscriptionCatalogView.showInternalErrorMessage()
                        }
                    }

                    override fun onFailure() {
                        mSuscriptionCatalogView.showInternalErrorMessage()
                    }
                })
    }

    override fun retrieveLimit(number: String, auth_credential: String) {
        mNetMobileRemoteDataSource.retrieveSuscriptionLimit(SuscriptionLimitRequest(auth_credential),
                object: ISuscriptionLimitCallback{
                    override fun onSuccess(response: SuscriptionLimitResponse) {
                        RegistrationSession.instance.clubsRegistrationLimit = response.max
                        retrieveCatalog(number, auth_credential)
                    }

                    override fun onFailure() {
                        //en caso de error
                    }
                })
    }

    override fun retrieveActive(number: String, auth_credential: String, catalog: List<EVClub>) {
        mNetMobileRemoteDataSource.retrieveSucriptionActive(SuscriptionActiveRequest(number, auth_credential),
                object: ISuscriptionActiveCallback{
                    override fun onSuccess(response: List<SuscriptionActiveResponse>) {
                        if(response.count()>0){
                            mSuscriptionCatalogView.showClubSuscription(markRegisteredSuscriptions(catalog, response))
                        }
                    }

                    override fun onFailure() {
                        //en caso de error
                    }
                })
    }
    private fun markRegisteredSuscriptions(catalog: List<EVClub>, selected: List<SuscriptionActiveResponse>): List<EVClub>{
        for(item in catalog){
            for(secondItem in selected){
                if(item.id == secondItem.id){
                    item.isRemoteRegistered = true
                }
            }
        }
        return catalog
    }
}