package com.example.testtask.di

import com.example.testtask.constants.API_URL
import com.example.testtask.repository.api.MoviesApiClient
import com.example.testtask.utils.network.IApiClientFactory
import com.example.testtask.utils.network.IOkHttpBuilder
import org.koin.dsl.module

val networkModule = module {

    single { get<IOkHttpBuilder>().buildHttpClient() }
    single { get<IApiClientFactory>().createClient(API_URL, get(), MoviesApiClient::class.java) }
}
