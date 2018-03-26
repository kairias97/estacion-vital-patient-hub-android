package com.estacionvital.patienthub.data.api

import com.estacionvital.patienthub.util.EV_BLOG_HOST_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by kevin on 6/3/2018.
 */
class EVBlogAPI {
    val service: IEVBlogService?

    private constructor(){
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

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