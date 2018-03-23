package com.estacionvital.patienthubestacionvital.data.api

import com.estacionvital.patienthubestacionvital.util.EV_MAIN_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by kevin on 25/2/2018.
 */
class EstacionVitalAPI {
    public val service: IEstacionVitalService?

    private constructor(){
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(EV_MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        service = retrofit.create(IEstacionVitalService::class.java)
    }
    companion object {
        val instance:EstacionVitalAPI  by lazy {EstacionVitalAPI()}
    }
}