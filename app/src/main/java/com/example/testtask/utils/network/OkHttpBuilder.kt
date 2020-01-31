package com.example.testtask.utils.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpBuilder: IOkHttpBuilder {

    override fun buildHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .callTimeout(300, TimeUnit.SECONDS)
            .build()
    }

}