package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.BuildConfig
import com.estacionvital.patienthub.util.MAIN_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by dusti on 24/02/2018.
 */

//Clase wrapper para retrofit service para comunicación con el api de netmobile
class NetMobileAPI {
    public val service: INetMobileService?

    private constructor(){
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY

        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val client:OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        service = retrofit.create(INetMobileService::class.java)
    }
    companion object {
        val instance:NetMobileAPI  by lazy {NetMobileAPI()}
    }
}