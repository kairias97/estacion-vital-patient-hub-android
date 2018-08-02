package com.estacionvital.patienthub.data.remote

import android.util.Log
import com.estacionvital.patienthub.BuildConfig
import com.estacionvital.patienthub.data.api.NetMobileAPI
import com.estacionvital.patienthub.data.remote.Callbacks.*
import com.estacionvital.patienthub.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by dusti on 24/02/2018.
 */
//Clase que se comunica con retrofit para operaciones de api de netmobile
class NetMobileRemoteDataSource {
    //verificar numero movistar
    fun verifyNumber(numberData: NumberVerificationRequest, callback: IAuthRegistrationCallback){
        val authCall = NetMobileAPI.instance.service!!.verifyNumber(numberData)

        authCall.enqueue(object: Callback<NumberVerificationResponse>{
            override fun onResponse(call: Call<NumberVerificationResponse>?, response: Response<NumberVerificationResponse>?){
                response.let {
                    if(response!!.code() == 200){
                        callback.onSuccess(response.body()!!)
                    }
                    else if(response!!.code() == 500){
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("MovistarValidation " + response.code().toString(), response.raw().body().toString())

                        }
                        callback.onFailure()
                    } else {
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<NumberVerificationResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("VerifyNumber error", t.toString())
                }
                callback.onConnectionError()
            }
        })
    }
    //solicitar codigo sms
    fun requestSMSCode(data: SendSMSRequest, callback: ISendSMSCallback){
        val authCall = NetMobileAPI.instance.service!!.sendSMSCode(data)
        authCall.enqueue(object: Callback<SendSMSResponse>{
            override fun onFailure(call: Call<SendSMSResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("SendSMS error", t.toString())
                }
                callback.onConnectionError()
            }

            override fun onResponse(call: Call<SendSMSResponse>?, response: Response<SendSMSResponse>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else if(response!!.code() == 500){
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("SMSRequestCode " + response.code().toString(), response.raw().body().toString())

                    }
                    callback.onFailure()
                } else {
                    callback.onFailure()
                }
            }

        })
    }
    //validar codigo pin
    fun validatePinCode(data: ValidatePinRequest, callback: IValidatePinCallback) {
        val authCall = NetMobileAPI.instance.service!!.validatePin(data)
        authCall.enqueue(object:Callback<ValidatePinResponse> {
            override fun onFailure(call: Call<ValidatePinResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("SendSMS error", t.toString())
                }
                callback.onFailure()
            }

            override fun onResponse(call: Call<ValidatePinResponse>?, response: Response<ValidatePinResponse>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else if(response!!.code() == 500){
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("SMSRequestCode " + response.code().toString(), response.raw().body().toString())

                    }
                    callback.onFailure()
                } else {
                    callback.onFailure()
                }
            }

        })
    }
    //obtener catalogo de suscripciones
    fun retrieveSubscriptionCatalog(data: SuscriptionCatalogRequest, callback: ISuscriptionCatalogCallback){
        val authCall = NetMobileAPI.instance.service!!.retrieveSuscriptionCatalog(data)
        authCall.enqueue(object:Callback<List<EVClub>>{
            override fun onFailure(call: Call<List<EVClub>>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug"){
                    Log.e("SubscriptionCatalog", t.toString())
                }
            }

            override fun onResponse(call: Call<List<EVClub>>?, response: Response<List<EVClub>>?) {
                if(response!!.code()==200){
                    callback.onSuccess(response.body()!!)
                }
                else if(response!!.code() == 500){
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("SubscriptionCatalog" + response.code().toString(), response.raw().body().toString())
                    }
                    callback.onFailure()
                }
            }
        })
    }
    //obtener limite de suscrpciones
    fun retrieveSubscriptionLimit(data: SuscriptionLimitRequest, callback: ISuscriptionLimitCallback){
        val authCall = NetMobileAPI.instance.service!!.retrieveSuscriptionLimit(data)
        authCall.enqueue(object:Callback<SuscriptionLimitResponse>{
            override fun onResponse(call: Call<SuscriptionLimitResponse>?, response: Response<SuscriptionLimitResponse>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else if(response!!.code() == 500){
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("SubscriptionLimit" + response.code().toString(), response.raw().body().toString())
                    }
                    callback.onFailure()
                }
            }

            override fun onFailure(call: Call<SuscriptionLimitResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug"){
                    Log.e("SuscriptionLimit error", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    //obtener suscripciones activas
    fun retrieveSubscriptionActive(data: SuscriptionActiveRequest, callback: ISuscriptionActiveCallback){
        val authCall = NetMobileAPI.instance.service!!.retrieveSuscriptionActive(data)
        authCall.enqueue(object: Callback<List<SuscriptionActiveResponse>> {
            override fun onResponse(call: Call<List<SuscriptionActiveResponse>>?, response: Response<List<SuscriptionActiveResponse>>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else if(response!!.code() == 500){
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("SuscriptionActive" + response.code().toString(), response.raw().body().toString())
                    }
                    callback.onFailure()
                }
            }
            override fun onFailure(call: Call<List<SuscriptionActiveResponse>>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug"){
                    Log.e("SuscriptionActive error", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    //obtener total de suscripciones
    fun retrieveSubscriptionTotal(data: SuscriptionTotalRequest, callback: ISuscriptionTotalCallback){
        val authCall = NetMobileAPI.instance.service!!.retrieveSuscriptionTotal(data)
        authCall.enqueue(object: Callback<SuscriptionTotalResponse> {
            override fun onResponse(call: Call<SuscriptionTotalResponse>?, response: Response<SuscriptionTotalResponse>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else if(response!!.code() == 500){
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("SuscriptionActive" + response.code().toString(), response.raw().body().toString())
                    }
                    callback.onFailure()
                }
            }
            override fun onFailure(call: Call<SuscriptionTotalResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug"){
                    Log.e("SuscriptionActive error", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    //suscribir a club de EV
    fun subscribeToEVClub(data: ClubSubscriptionRequest, callback:INewClubSubscriptionCallback){
        val authCall = NetMobileAPI.instance.service!!.subscribeToEVClub(data)
        authCall.enqueue(object:Callback<ClubSubscriptionResponse>{
            override fun onFailure(call: Call<ClubSubscriptionResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug"){
                    Log.e("SubscriptionRegister", t.toString())
                }
                callback.onFailure()
            }

            override fun onResponse(call: Call<ClubSubscriptionResponse>?, response: Response<ClubSubscriptionResponse>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else if(response!!.code() == 500){
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("SubscriptionRegister" + response.code().toString(), response.raw().body().toString())
                    }
                    callback.onFailure()
                }
            }


        })
    }
    private constructor()
    companion object {
        val INSTANCE: NetMobileRemoteDataSource by lazy { NetMobileRemoteDataSource()}
    }
}




