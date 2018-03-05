package com.estacionvital.patienthub.data.remote

import android.util.Log
import com.estacionvital.patienthub.BuildConfig
import com.estacionvital.patienthub.data.api.EstacionVitalAPI
import com.estacionvital.patienthub.data.remote.Callbacks.IEVProfileUpdateCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRegistrationSubmittedCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IEVRetrieveProfileCallback
import com.estacionvital.patienthub.data.remote.Callbacks.IValidateEVCredentialsCallback
import com.estacionvital.patienthub.model.*
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
    fun retrieveEVUserProfile(token: String, callback: IEVRetrieveProfileCallback){
        val authCall = EstacionVitalAPI.instance.service!!.retrieveProfileData(token)
        authCall.enqueue(object: Callback<EVRetrieveProfileResponse>{
            override fun onFailure(call: Call<EVRetrieveProfileResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("EVRetrivingProfile error", t.toString())
                }
                callback.onFailure()
            }

            override fun onResponse(call: Call<EVRetrieveProfileResponse>?, response: Response<EVRetrieveProfileResponse>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else{
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("EVRetrieveingProfie error " + response.code().toString(), response.raw().body().toString())
                    }
                    callback.onFailure()
                }
            }
        })
    }
    fun updateEVProfile(token: String, data: EVProfileUpdateRequest, callback: IEVProfileUpdateCallback){
        val authCall = EstacionVitalAPI.instance.service!!.updateProfile(token, data)
        authCall.enqueue(object: Callback<EVProfileUpdateResponse>{
            override fun onResponse(call: Call<EVProfileUpdateResponse>?, response: Response<EVProfileUpdateResponse>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else{
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("EVUpdateProfie error " + response.code().toString(), response.raw().body().toString())
                    }
                    callback.onFailure()
                }
            }

            override fun onFailure(call: Call<EVProfileUpdateResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("EVRUpdateProfile error", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    companion object {
        val INSTANCE: EstacionVitalRemoteDataSource by lazy { EstacionVitalRemoteDataSource()}
    }
}