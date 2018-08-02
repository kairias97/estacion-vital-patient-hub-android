package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.BuildConfig
import com.estacionvital.patienthub.util.EV_MAIN_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by kevin on 25/2/2018.
 */

//Clase wrapper para retrofit service para comunicación con el api de ev
class EstacionVitalAPI {
    public val service: IEstacionVitalService?

    private constructor(){
        //Preparacion de retrofit
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY

        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

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
    //Uso de singleton para el retrofit
    companion object {
        val instance:EstacionVitalAPI  by lazy {EstacionVitalAPI()}
    }
}