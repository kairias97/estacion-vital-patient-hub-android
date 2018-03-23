package com.estacionvital.patienthubestacionvital.presenter.implementations

import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.INewClubSubscriptionCallback
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.ISuscriptionActiveCallback
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.ISuscriptionCatalogCallback
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.ISuscriptionLimitCallback
import com.estacionvital.patienthubestacionvital.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthubestacionvital.model.*
import com.estacionvital.patienthubestacionvital.presenter.ISuscriptionCatalogPresenter
import com.estacionvital.patienthubestacionvital.ui.views.IClubSubscriptionView
import com.estacionvital.patienthubestacionvital.util.NETMOBILE_AUTH_CREDENTIAL

/**
 * Created by dusti on 26/02/2018.
 */
class ClubSubscriptionPresenterImpl : ISuscriptionCatalogPresenter {
    override fun validateSubscriptions() {
        val subscriptions: MutableList<EVClub> = mSubscriptionCatalogView.getSelectedClubs().toMutableList()
        when {
            //No subscriptions selected
            subscriptions.count() == 0 -> {
                mSubscriptionCatalogView.showSubscriptionsRequiredMessage()
            }
            //Only past subscriptions
            subscriptions.count{!it.isRemoteRegistered} == 0 -> {
                mSubscriptionCatalogView.navigateToEVRegistration()
            }
            //There are subscriptions to be added
            else -> {
                subscribeNewClubs(subscriptions.filter { !it.isRemoteRegistered }.toMutableList(), 0)
            }

        }

    }

    private fun subscribeNewClubs(newClubs: MutableList<EVClub>, index:Int){
        mSubscriptionCatalogView.showSubscriptionProgress(newClubs[index].name)
        mNetMobileRemoteDataSource.subscribeToEVClub(
                ClubSubscriptionRequest(RegistrationSession.instance.phoneNumber,
                        NETMOBILE_AUTH_CREDENTIAL, newClubs[index].id.toInt()),
                object:INewClubSubscriptionCallback{
                    override fun onSuccess(result: ClubSubscriptionResponse) {

                        mSubscriptionCatalogView.hideSubscriptionProgress()
                        //For handling success
                        if(result.status != 1) {
                            mSubscriptionCatalogView.showCustomWSMessage(result.msg)
                            return
                        } else {
                            mSubscriptionCatalogView.showNewClubSuccessMessage(newClubs[index].name)
                            newClubs[index].isRemoteRegistered = true

                            mSubscriptionCatalogView.updateClubSubscriptionView(newClubs[index])
                        }
                        if (index == newClubs.size - 1) {
                            mSubscriptionCatalogView.showSubscriptionSuccessMessage()
                            mSubscriptionCatalogView.navigateToEVRegistration()
                        } else {
                            subscribeNewClubs(newClubs, index + 1)
                        }
                    }

                    override fun onFailure() {
                        mSubscriptionCatalogView.hideSubscriptionProgress()
                        mSubscriptionCatalogView.showInternalErrorMessage()

                    }

                })
    }

    private val mSubscriptionCatalogView: IClubSubscriptionView
    private val mNetMobileRemoteDataSource: NetMobileRemoteDataSource
    private lateinit var suscriptionLimit: SuscriptionLimitResponse
    private lateinit var suscriptionCatalog: List<EVClub>

    constructor(clubSubscriptionView: IClubSubscriptionView, netMobileRemoteDataSource: NetMobileRemoteDataSource){
        this.mSubscriptionCatalogView = clubSubscriptionView
        this.mNetMobileRemoteDataSource = netMobileRemoteDataSource
    }

    override fun retrieveCatalog(number: String, auth_credential: String) {
        mSubscriptionCatalogView.showRetrievingCatalogProcess()
        mNetMobileRemoteDataSource.retrieveSubscriptionCatalog(SuscriptionCatalogRequest(auth_credential),
                object: ISuscriptionCatalogCallback{
                    override fun onSuccess(response: List<EVClub>) {
                        mSubscriptionCatalogView.hideRetrievingCatalogProcess()
                        if(response.count()>0){
                            retrieveActive(number, auth_credential, response)
                        }
                    }

                    override fun onFailure() {
                        mSubscriptionCatalogView.hideRetrievingCatalogProcess()
                        mSubscriptionCatalogView.showInternalErrorMessage()
                    }
                })
    }

    override fun retrieveLimit(number: String, auth_credential: String) {
        mNetMobileRemoteDataSource.retrieveSubscriptionLimit(SuscriptionLimitRequest(auth_credential),
                object: ISuscriptionLimitCallback{
                    override fun onSuccess(response: SuscriptionLimitResponse) {
                        if (response.status != 1) {
                            mSubscriptionCatalogView.showCustomWSMessage(response.msg)
                        } else {
                            RegistrationSession.instance.clubsRegistrationLimit = response.max
                            retrieveCatalog(number, auth_credential)

                        }
                    }

                    override fun onFailure() {
                        mSubscriptionCatalogView.showInternalErrorMessage()
                    }
                })
    }

    override fun retrieveActive(number: String, auth_credential: String, catalog: List<EVClub>) {
        mSubscriptionCatalogView.showActiveSubscriptionsProgress()
        mNetMobileRemoteDataSource.retrieveSubscriptionActive(SuscriptionActiveRequest(number, auth_credential),
                object: ISuscriptionActiveCallback{
                    override fun onSuccess(response: List<SuscriptionActiveResponse>) {
                        mSubscriptionCatalogView.hideActiveSubscriptionsProgress()

                        mSubscriptionCatalogView.showClubSubscription(
                                        markRegisteredSuscriptions(catalog.toMutableList(),
                                        response))

                    }

                    override fun onFailure() {
                        mSubscriptionCatalogView.hideActiveSubscriptionsProgress()
                        mSubscriptionCatalogView.showInternalErrorMessage()
                    }
                })
    }

    override fun validateSelectedClub(club: EVClub) {
        val selectedClubsCount: Int = mSubscriptionCatalogView.getSelectedClubsCount()
        if (club.isRemoteRegistered) return

        if (!club.isSelected && selectedClubsCount >= RegistrationSession.instance.clubsRegistrationLimit ) {
            //Show validation message here
            mSubscriptionCatalogView.showLimitSubscriptionMessage()
        } else {
            club.isSelected = !club.isSelected
        }
        mSubscriptionCatalogView.updateClubSubscriptionView(club)
    }
    private fun markRegisteredSuscriptions(
            catalog: MutableList<EVClub>,
            registeredClubs: List<SuscriptionActiveResponse>): MutableList<EVClub>{
        for(item in catalog){
            //To check if any registered clubs
            item.isRemoteRegistered = registeredClubs.any { it.id == item.id }
        }
        return catalog
    }
}