package com.estacionvital.patienthubestacionvital.data.remote

import android.util.Log
import com.estacionvital.patienthubestacionvital.BuildConfig
import com.estacionvital.patienthubestacionvital.data.api.EstacionVitalAPI
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.*
import com.estacionvital.patienthubestacionvital.model.*
import com.estacionvital.patienthubestacionvital.util.EXAMINATION_TYPE_CHAT
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
                    Log.e("EVRetrieve error", t.toString())
                }
                callback.onFailure()
            }

            override fun onResponse(call: Call<EVRetrieveProfileResponse>?, response: Response<EVRetrieveProfileResponse>?) {
                if(response!!.code() == 200){
                    callback.onSuccess(response.body()!!)
                }
                else{
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        Log.e("EVRetrieve error " + response.code().toString(), response.raw().body().toString())
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
    fun logout(token: String, callback: ILogoutCallback){
        val authCall = EstacionVitalAPI.instance.service!!.logout(token)
        authCall.enqueue(object: Callback<LogoutResponse>{
            override fun onFailure(call: Call<LogoutResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Logout error", t.toString())
                }
                callback.onFailure()
            }

            override fun onResponse(call: Call<LogoutResponse>?, response: Response<LogoutResponse>?) {
                when (response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Logout unsuccessful", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

        })
    }
    fun retrieveSpecialtiesChat(token: String, callback: IEVRetrieveSpecialtiesCallback){
        val authCall = EstacionVitalAPI.instance.service!!.retrieveSpecialties(token)
        authCall.enqueue(object: Callback<EVSpecialtiesResponse>{
            override fun onResponse(call: Call<EVSpecialtiesResponse>?, response: Response<EVSpecialtiesResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Retrieve Specialties unsuccessful", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVSpecialtiesResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Retrieve Specialties unsuccessful", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    fun retrieveExaminationsHistory(token: String, callback: IEVRetrieveUserExaminationsHIstoryCalllback){
        val authCall = EstacionVitalAPI.instance.service!!.retrieveExaminations(token, EVRetrieveUserExaminationRequest(EXAMINATION_TYPE_CHAT))
        authCall.enqueue(object: Callback<EVRetrieveUserExaminationResponse>{
            override fun onResponse(call: Call<EVRetrieveUserExaminationResponse>?, response: Response<EVRetrieveUserExaminationResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Retrieve Examinations history unsuccessful", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVRetrieveUserExaminationResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Retrieve Examinations history unsuccessful", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    fun retrieveDoctorsAvailability(token: String, specialty: String, callback: IEVRetrieveDoctorsAvailabilityCallBack){
        val authCall = EstacionVitalAPI.instance.service!!.retrieveDoctorsAvailability(token, EVRetrieveDoctorsAvailabilityRequest(specialty))
        authCall.enqueue(object: Callback<EVRetrieveDoctorsAvailabilityResponse>{
            override fun onResponse(call: Call<EVRetrieveDoctorsAvailabilityResponse>?, response: Response<EVRetrieveDoctorsAvailabilityResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Retrieve Doctor's Availability unsuccessful", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVRetrieveDoctorsAvailabilityResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Retrieve Doctor's Availability unsuccessful", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    fun createNewExamination(token: String, specialty: String, service_type: String, callback: IEVCreateNewExaminationCallBack){
        val authCall = EstacionVitalAPI.instance.service!!.createNewExamination(token, EVCreateNewExaminationRequest(specialty, EXAMINATION_TYPE_CHAT, service_type))
        authCall.enqueue(object: Callback<EVCreateNewExaminationResponse>{
            override fun onResponse(call: Call<EVCreateNewExaminationResponse>?, response: Response<EVCreateNewExaminationResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Create new room unsuccessful", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVCreateNewExaminationResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Create new room unsuccessful", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    fun validateCoupon(token: String, coupon: String, callback: IEVValidateCouponCallBack){
        val authCall = EstacionVitalAPI.instance.service!!.validateCoupon(token, EVValidateCouponRequest(coupon))
        authCall.enqueue(object: Callback<EVValidateCouponResponse>{
            override fun onResponse(call: Call<EVValidateCouponResponse>?, response: Response<EVValidateCouponResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Validate coupon unsuccessful", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVValidateCouponResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Validate coupon unsuccessful", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    companion object {
        val INSTANCE: EstacionVitalRemoteDataSource by lazy { EstacionVitalRemoteDataSource()}
    }
}