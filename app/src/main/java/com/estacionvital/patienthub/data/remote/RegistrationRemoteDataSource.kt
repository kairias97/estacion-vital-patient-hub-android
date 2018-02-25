package com.estacionvital.patienthub.data.remote

import android.util.Log
import com.estacionvital.patienthub.NumberVerification
import com.estacionvital.patienthub.NumberVerificationResponse
import com.estacionvital.patienthub.data.api.NetMobileAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by dusti on 24/02/2018.
 */
class RegistrationRemoteDataSource {
    interface AuthRegistrationCallback{
        fun onValidNumber(response: NumberVerificationResponse)
        fun onWrongNumber()
        fun onVerificationFailed()
    }
    fun verifyNumber(numberData: NumberVerification, callback: AuthRegistrationCallback){
        val authCall = NetMobileAPI.instance.service!!.verifyNumber(numberData)

        authCall.enqueue(object: Callback<NumberVerificationResponse>{
            override fun onResponse(call: Call<NumberVerificationResponse>?, response: Response<NumberVerificationResponse>?){
                response.let {
                    if(response!!.code() == 200){
                        callback.onValidNumber(response.body()!!)
                    }
                    else if(response!!.code() == 500){
                        Log.e("AuthUserCall " + response.code().toString(), response.raw().body().toString())
                        callback.onVerificationFailed()
                    }
                }
            }

            override fun onFailure(call: Call<NumberVerificationResponse>?, t: Throwable?) {
                Log.e("RemoteUserAuthError", t.toString())
            }
        })
    }
    companion object {
        val instance:RegistrationRemoteDataSource by lazy {RegistrationRemoteDataSource()}
    }
}