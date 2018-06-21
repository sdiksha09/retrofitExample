package com.kotlinallsample.retrofitsample

import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Nilesh Birhade on 19-06-2018.
 */
open class RetroRequestBuilder {
    internal var restAdapter: Retrofit? = null
    var URL = "http://qa.mgfm.in/"

    protected fun build(): Retrofit {
        if (restAdapter == null) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val gson = GsonBuilder()
                    .serializeNulls()
                    // .setLenient()
                    .create()

            val okHttpClient = okhttp3.OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .build()

            restAdapter = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

        }
        return restAdapter as Retrofit
    }
}