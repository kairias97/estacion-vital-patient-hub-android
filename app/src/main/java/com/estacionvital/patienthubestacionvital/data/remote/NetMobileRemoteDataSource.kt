package com.estacionvital.patienthubestacionvital.data.remote

import android.util.Log
import com.estacionvital.patienthubestacionvital.BuildConfig
import com.estacionvital.patienthubestacionvital.data.api.NetMobileAPI
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.*
import com.estacionvital.patienthubestacionvital.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by dusti on 24/02/2018.
 */
class NetMobileRemoteDataSource {

    fun verifyNumber(numberData: NumberVerificationRequest, callback: AuthRegistrationCallback){
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
                callback.onFailure()
            }
        })
    }

    fun requestSMSCode(data: SendSMSRequest, callback: ISendSMSCallback){
        val authCall = NetMobileAPI.instance.service!!.sendSMSCode(data)
        authCall.enqueue(object: Callback<SendSMSResponse>{
            override fun onFailure(call: Call<SendSMSResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("SendSMS error", t.toString())
                }
                callback.onFailure()
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




