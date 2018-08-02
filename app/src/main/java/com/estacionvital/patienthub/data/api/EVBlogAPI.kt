package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.BuildConfig
import com.estacionvital.patienthub.util.EV_BLOG_HOST_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by kevin on 6/3/2018.
 */
//Clase wrapper para retrofit service para comunicación con el api del blog
class EVBlogAPI {
    val service: IEVBlogService?

    private constructor(){
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
                .baseUrl(EV_BLOG_HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        service = retrofit.create(IEVBlogService::class.java)
    }
    companion object {
        val instance:EVBlogAPI  by lazy {EVBlogAPI()}
    }
}