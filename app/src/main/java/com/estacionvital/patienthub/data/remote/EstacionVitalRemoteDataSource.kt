package com.estacionvital.patienthub.data.remote

import android.util.Log
import com.estacionvital.patienthub.BuildConfig
import com.estacionvital.patienthub.data.api.EstacionVitalAPI
import com.estacionvital.patienthub.data.remote.Callbacks.*
import com.estacionvital.patienthub.model.*
import com.estacionvital.patienthub.util.EXAMINATION_TYPE_CHAT
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
                else if(response!!.code() == 401 || response!!.code() == 403) {
                    callback.onTokenExpired()
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
                else if(response!!.code() == 401 || response!!.code() == 403) {
                    callback.onTokenExpired()
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
                    401, 403 -> {
                        callback.onTokenExpired()
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
                    401, 403 -> {
                        callback.onTokenExpired()
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Error", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVSpecialtiesResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Failure", t.toString())
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
                    401, 403 -> {
                        callback.onTokenExpired()
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Error", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVRetrieveUserExaminationResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Failure", t.toString())
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

                    401, 403 -> {
                        callback.onTokenExpired()
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Failure", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVRetrieveDoctorsAvailabilityResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Failure", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    fun createNewExamination(token: String, specialty: String, service_type: String, type: String, code: String, order_id: String,callback: IEVCreateNewExaminationCallBack){
        val authCall = EstacionVitalAPI.instance.service!!.createNewExamination(token, EVCreateNewExaminationRequest(specialty, EXAMINATION_TYPE_CHAT, service_type,
                EVPaymentInfo(type, code, order_id)))
        authCall.enqueue(object: Callback<EVCreateNewExaminationResponse>{
            override fun onResponse(call: Call<EVCreateNewExaminationResponse>?, response: Response<EVCreateNewExaminationResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401 -> {
                        callback.onChatCreationDenied()
                    }
                    403 -> {
                        callback.onTokenExpired()
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Error", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVCreateNewExaminationResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Failure", t.toString())
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
                    401, 403 -> {
                        callback.onTokenExpired()
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Error", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVValidateCouponResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("Failure", t.toString())
                }
                callback.onFailure()
            }
        })
    }

    fun getChannelByID(token: String, body: EVGetChannelByIDRequest, callback:IGetChannelByUniqueName) {
        val authCall = EstacionVitalAPI.instance.service!!.getChannelByUniqueName(token, body)
        authCall.enqueue(object:Callback<EVUserExaminationByIDResponse> {
            override fun onFailure(call: Call<EVUserExaminationByIDResponse>?, t: Throwable?) {
                callback.onFailure()
            }

            override fun onResponse(call: Call<EVUserExaminationByIDResponse>?, response: Response<EVUserExaminationByIDResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401, 403 -> {
                        callback.onTokenExpired()
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Error", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

        })
    }
    fun getDocuments(token: String, callback: IGetDocumentsCallback){
        val authCall = EstacionVitalAPI.instance.service!!.getDocuments(token)
        authCall.enqueue(object: Callback<DocumentsResponse>{
            override fun onFailure(call: Call<DocumentsResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("failure", t.toString())
                }
                callback.onFailure()
            }

            override fun onResponse(call: Call<DocumentsResponse>?, response: Response<DocumentsResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401, 403 -> {
                        callback.onTokenExpired()
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Not 200", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

        })
    }
    fun paymentCreditCard(token: String, holder: String, expYear: String, expMonth: String, number: String, cvc: String, callback: IEVPaymentCreditCardCallBack){
        val authCall = EstacionVitalAPI.instance.service!!.paymentCreditCard(token, EVCreditCardRequest(holder, number, expMonth, expYear, cvc))
        authCall.enqueue(object: Callback<EVCreditCardResponse>{
            override fun onResponse(call: Call<EVCreditCardResponse>?, response: Response<EVCreditCardResponse>?) {
                when(response!!.code()){
                    200 -> {
                        callback.onSuccess(response!!.body()!!)
                    }
                    401, 403 -> {
                        callback.onTokenExpired()
                    }
                    else -> {
                        if(BuildConfig.BUILD_TYPE == "debug") {
                            Log.e("Not 200", response.raw().body().toString())
                        }
                        callback.onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<EVCreditCardResponse>?, t: Throwable?) {
                if(BuildConfig.BUILD_TYPE == "debug") {
                    Log.e("failure", t.toString())
                }
                callback.onFailure()
            }
        })
    }
    companion object {
        val INSTANCE: EstacionVitalRemoteDataSource by lazy { EstacionVitalRemoteDataSource()}
    }
}