package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.INewClubSubscriptionCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionActiveCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionCatalogCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionLimitCallback
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.ISuscriptionCatalogPresenter
import com.estacionvital.patienthub.ui.views.IClubSubscriptionView
import com.estacionvital.patienthub.util.NETMOBILE_AUTH_CREDENTIAL

/**
 * Created by dusti on 26/02/2018.
 */
class ClubSubscriptionPresenterImpl : ISuscriptionCatalogPresenter {
    override fun validateSubscriptions(isLoggedInUser: Boolean) {
        val subscriptions: MutableList<EVClub> = mSubscriptionCatalogView.getSelectedClubs().toMutableList()
        when {
            //No subscriptions selected
            subscriptions.count() == 0 -> {

                mSubscriptionCatalogView.showSubscriptionsRequiredMessage()
            }
            //Only past subscriptions
            subscriptions.count{!it.isRemoteRegistered} == 0 -> {
                if (isLoggedInUser) {
                    mSubscriptionCatalogView.navigateToMain()
                } else {
                    mSubscriptionCatalogView.navigateToEVRegistration()
                }

            }
            //There are subscriptions to be added
            else -> {
                val phoneNumber = if (isLoggedInUser) EVUserSession.instance.phoneNumber else RegistrationSession.instance.phoneNumber
                subscribeNewClubs(subscriptions.filter { !it.isRemoteRegistered }.toMutableList(), 0, isLoggedInUser, phoneNumber)
            }

        }

    }

    private fun subscribeNewClubs(newClubs: MutableList<EVClub>, index:Int, isLoggedInUser: Boolean, phoneNumber: String){
        mSubscriptionCatalogView.showSubscriptionProgress(newClubs[index].name)
        mNetMobileRemoteDataSource.subscribeToEVClub(
                ClubSubscriptionRequest(phoneNumber,
                        NETMOBILE_AUTH_CREDENTIAL, newClubs[index].id.toInt()),
                object:INewClubSubscriptionCallback{
                    override fun onSuccess(result: ClubSubscriptionResponse) {

                        mSubscriptionCatalogView.hideSubscriptionProgress()
                        //For handling success
                        if(result.status != 1) {
                            mSubscriptionCatalogView.showCustomWSMessage(result.msg)
                            return
                        } else {
                            //On success of processed element, we update UI in order to keep updated
                            //the track of added club suscriptions
                            mSubscriptionCatalogView.showNewClubSuccessMessage(newClubs[index].name)
                            newClubs[index].isRemoteRegistered = true

                            mSubscriptionCatalogView.updateClubSubscriptionView(newClubs[index])
                        }
                        //If the last element was processed successfully
                        if (index == newClubs.size - 1) {
                            mSubscriptionCatalogView.showSubscriptionSuccessMessage()
                            if (isLoggedInUser) {
                                mSubscriptionCatalogView.navigateToMain()
                            } else {
                                mSubscriptionCatalogView.navigateToEVRegistration()
                            }

                        } else {
                            subscribeNewClubs(newClubs, index + 1, isLoggedInUser, phoneNumber)
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

    override fun retrieveLimit(isLoggedInUser: Boolean, auth_credential: String) {
        val phoneNumber = if (isLoggedInUser) EVUserSession.instance.phoneNumber else RegistrationSession.instance.phoneNumber

        mNetMobileRemoteDataSource.retrieveSubscriptionLimit(SuscriptionLimitRequest(auth_credential),
                object: ISuscriptionLimitCallback{
                    override fun onSuccess(response: SuscriptionLimitResponse) {
                        if (response.status != 1) {
                            mSubscriptionCatalogView.showCustomWSMessage(response.msg)
                        } else {
                            RegistrationSession.instance.clubsRegistrationLimit = response.max
                            retrieveCatalog(phoneNumber, auth_credential)

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