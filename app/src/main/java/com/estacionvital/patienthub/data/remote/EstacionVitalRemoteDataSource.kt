package com.estacionvital.patienthub.data.remote

import android.util.Log
import com.estacionvital.patienthub.BuildConfig
import com.estacionvital.patienthub.data.api.EstacionVitalAPI
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRegistrationSubmittedCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IValidateEVCredentialsCallback
import com.estacionvital.patienthub.model.EVRegistrationRequest
import com.estacionvital.patienthub.model.EVRegistrationResponse
import com.estacionvital.patienthub.model.LoginRequest
import com.estacionvital.patienthub.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by kevin on 25/2/2018.
 */
class EstacionVitalRemoteDataSource {
    private constructor()

    fun validateEVCredentials(data: LoginRequest, callback: IValidateEVCredentialsCallback) {
        val authCall = EstacionVitalAPI.instance.service!!.validateEVCredentials(data)
        authCall.enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("VerifyNumber error", t.toString())
                }
                callback.onFailure()            }

            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else if(response!!.code() == 500){
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("EVLoginValidation " + response.code().toString(), response.raw().body().toString())

                    }
                    callback.onFailure()
                } else {
                    callback.onFailure()
                }
            }

        })
    }
    fun submitEVRegistration(basicAuth: String, data: EVRegistrationRequest,
                             callback: IEVRegistrationSubmittedCallback) {
        val authCall = EstacionVitalAPI.instance.service!!.submitRegistrationData(basicAuth,
                data)
        authCall.enqueue(object: Callback<EVRegistrationResponse> {
            override fun onFailure(call: Call<EVRegistrationResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("EVRegistration error", t.toString())
                }
                callback.onFailure()            }

            override fun onResponse(call: Call<EVRegistrationResponse>?, response: Response<EVRegistrationResponse>?) {
                when(response!!.code()){
                    200 -> callback.onSuccess(response.body()!!)
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("EVRegistration " + response.code().toString(), response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }

            }

        })
    }
    companion object {
        val INSTANCE: EstacionVitalRemoteDataSource by lazy { EstacionVitalRemoteDataSource()}
    }
}