package com.estacionvital.patienthub.data.remote

import android.os.Build
import android.util.Log
import com.estacionvital.patienthub.BuildConfig
import com.estacionvital.patienthub.data.api.NetMobileAPI
import com.estacionvital.patienthub.data.remote.Callbacks.AuthRegistrationCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISendSMSCallback
import com.estacionvital.patienthub.data.remote.Callbacks.ISuscriptionCatalogCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IValidatePinCallback
import com.estacionvital.patienthub.model.*
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
    fun retrieveSuscriptionCatalog(data: SuscriptionCatalogRequest, callback: ISuscriptionCatalogCallback){
        val authCall = NetMobileAPI.instance.service!!.retrieveSuscriptionCatalog(data)
        authCall.enqueue(object:Callback<List<SuscriptionCatalogResponse>>{
            override fun onFailure(call: Call<List<SuscriptionCatalogResponse>>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug"){
                    Log.e("SuscriptionCatalog error", t.toString())
                }
            }

            override fun onResponse(call: Call<List<SuscriptionCatalogResponse>>?, response: Response<List<SuscriptionCatalogResponse>>?) {
                if(response!!.code()==200){
                    callback.onSucces(response.body()!!)
                }
                else if(response!!.code() == 500){
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("SuscriptionCatalog error " + response.code().toString(), response.raw().body().toString())
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


