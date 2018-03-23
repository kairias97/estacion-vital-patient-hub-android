package com.estacionvital.patienthubestacionvital.data.api

import com.estacionvital.patienthubestacionvital.util.MAIN_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by dusti on 24/02/2018.
 */
class NetMobileAPI {
    public val service: INetMobileService?

    private constructor(){
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

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