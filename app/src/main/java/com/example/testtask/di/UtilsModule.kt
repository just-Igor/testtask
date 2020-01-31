package com.example.testtask.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.testtask.utils.network.IInternetConnectionHelper
import com.example.testtask.utils.network.InternetConnectionHelper
import com.example.testtask.utils.network.ApiClientFactory
import com.example.testtask.utils.network.IApiClientFactory
import com.example.testtask.utils.network.IOkHttpBuilder
import com.example.testtask.utils.network.OkHttpBuilder
import com.example.testtask.utils.rx.ISchedulerProvider
import com.example.testtask.utils.rx.SchedulerProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {

    factory<ISchedulerProvider> { SchedulerProvider() }

    factory<IInternetConnectionHelper> {
        InternetConnectionHelper(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }

    factory<IOkHttpBuilder> { OkHttpBuilder() }

    factory<IApiClientFactory> { ApiClientFactory() }

}