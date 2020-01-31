package com.example.testtask.di

import com.example.testtask.utils.network.IInternetConnectionHelper
import com.example.testtask.utils.network.TestInternetConnectionHelper
import com.example.testtask.utils.network.ApiClientFactory
import com.example.testtask.utils.network.IApiClientFactory
import com.example.testtask.utils.network.IOkHttpBuilder
import com.example.testtask.utils.network.OkHttpBuilder
import com.example.testtask.utils.rx.ISchedulerProvider
import com.example.testtask.utils.rx.TestSchedulerProvider
import org.koin.dsl.module

val testUtilsModule = module {

    factory<ISchedulerProvider> { TestSchedulerProvider() }

    factory<IInternetConnectionHelper> { TestInternetConnectionHelper() }

    factory<IOkHttpBuilder> { OkHttpBuilder() }

    factory<IApiClientFactory> { ApiClientFactory() }

}

val testModules = listOf(
    testUtilsModule,
    databaseModule,
    navigationModule,
    networkModule,
    movieModule
)