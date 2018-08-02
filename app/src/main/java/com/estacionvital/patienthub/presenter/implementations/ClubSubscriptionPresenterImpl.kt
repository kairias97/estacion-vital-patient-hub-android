package com.estacionvital.patienthub.presenter.implementations

import com.estacionvital.patienthub.data.remote.Callbacks.*
import com.estacionvital.patienthub.data.remote.NetMobileRemoteDataSource
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.presenter.ISuscriptionCatalogPresenter
import com.estacionvital.patienthub.ui.views.IClubSubscriptionView
import com.estacionvital.patienthub.util.MAX_TOTAL_SUSCRIPTIONS
import com.estacionvital.patienthub.util.NETMOBILE_AUTH_CREDENTIAL

/**
 * Created by dusti on 26/02/2018.
 */
//Presenter para lógica de la suscripcion de clubes
class ClubSubscriptionPresenterImpl : ISuscriptionCatalogPresenter {
    override fun validateSubscriptions(isLoggedInUser: Boolean) {
        val subscriptions: MutableList<EVClub> = mSubscriptionCatalogView.getSelectedClubs().toMutableList()
        when {
            //No subscriptions selected, para mostrar mensaje
            subscriptions.count() == 0 -> {

                mSubscriptionCatalogView.showSubscriptionsRequiredMessage()
            }
            //Solo suscripciones ya realizadas
            subscriptions.count{!it.isRemoteRegistered} == 0 -> {
                //Si tiene sesion iniciada va a main, caso contrario va a registro
                if (isLoggedInUser) {
                    mSubscriptionCatalogView.navigateToMain()
                } else {
                    mSubscriptionCatalogView.navigateToEVRegistration()
                }

            }
            //Significa que hay suscripciones a ser agregadas
            else -> {
                val phoneNumber = if (isLoggedInUser) EVUserSession.instance.phoneNumber else RegistrationSession.instance.phoneNumber
                subscribeNewClubs(subscriptions.filter { !it.isRemoteRegistered }.toMutableList(), 0, isLoggedInUser, phoneNumber)
            }

        }

    }
    //Suscripcion a clubes, este método es recursivo
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
                            RegistrationSession.instance.netmobileTotalSuscriptions += 1
                            mSubscriptionCatalogView.updateClubSubscriptionView(newClubs[index])
                        }
                        //Si el ultimo elemento a procesar se realizo con exito
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
    //Tercera peticion que se hace al api para obtener catalogo de clubes
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
    //Segunda peticion que se hace al api para obtener el total de suscripciones
    private fun retrieveTotalSuscriptions(number: String, auth_credential: String) {

        mNetMobileRemoteDataSource.retrieveSubscriptionTotal(SuscriptionTotalRequest(number, auth_credential),
                object: ISuscriptionTotalCallback {
                    override fun onSuccess(response: SuscriptionTotalResponse) {
                        if (response.status == 1) {
                            RegistrationSession.instance.netmobileTotalSuscriptions = response.total
                            retrieveCatalog(number, auth_credential)
                        } else  {
                            mSubscriptionCatalogView.showCustomWSMessage(response.msg)
                        }
                    }

                    override fun onFailure() {
                        mSubscriptionCatalogView.showInternalErrorMessage()
                    }

                })

    }

    //first petition para obtener el limite de suscripciones del api y saber el total de limite de ev
    //asi como el total de los clubes de netmobile
    override fun retrieveLimit(isLoggedInUser: Boolean, auth_credential: String) {
        val phoneNumber = if (isLoggedInUser) EVUserSession.instance.phoneNumber else RegistrationSession.instance.phoneNumber

        mNetMobileRemoteDataSource.retrieveSubscriptionLimit(SuscriptionLimitRequest(auth_credential),
                object: ISuscriptionLimitCallback{
                    override fun onSuccess(response: SuscriptionLimitResponse) {
                        if (response.status != 1) {
                            mSubscriptionCatalogView.showCustomWSMessage(response.msg)
                        } else {
                            RegistrationSession.instance.evClubsRegistrationLimit = response.max
                            RegistrationSession.instance.totalClubsRegistrationLimit = MAX_TOTAL_SUSCRIPTIONS
                            retrieveTotalSuscriptions(phoneNumber, auth_credential)

                        }
                    }

                    override fun onFailure() {
                        mSubscriptionCatalogView.showInternalErrorMessage()
                    }
                })
    }

    //Fourth request made via api para obtener los clubes activos y hacer un match
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
    //Validacion de un club seleccionado en la UI
    override fun validateSelectedClub(club: EVClub) {
        val selectedClubsCount: Int = mSubscriptionCatalogView.getSelectedClubsCount()
        val selectedNotRemoteClubsCount: Int = mSubscriptionCatalogView.getNewSelectedClubsCount()
        if (club.isRemoteRegistered) return

        //We validate if it was not selected, if we dont surpass the limit of selected ev clubs
        //Finally we validate that we dont overpass the limit of MAX_TOTAL_SUSCRIPTIONS
        if (!club.isSelected
                && (selectedClubsCount >= RegistrationSession.instance.evClubsRegistrationLimit
                || RegistrationSession.instance.netmobileTotalSuscriptions + selectedNotRemoteClubsCount >= MAX_TOTAL_SUSCRIPTIONS)) {

            //Show validation message here
            mSubscriptionCatalogView.showLimitSubscriptionMessage()
        } else {
            club.isSelected = !club.isSelected
        }
        mSubscriptionCatalogView.updateClubSubscriptionView(club)
    }
    //Marcar suscripciones como registradas
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