package com.example.testtask.utils.network

import okhttp3.OkHttpClient

interface IApiClientFactory {

    fun<Client> createClient(url: String, httpClient: OkHttpClient, classType: Class<Client>): Client

}