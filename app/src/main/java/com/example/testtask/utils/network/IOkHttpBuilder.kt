package com.example.testtask.utils.network

import okhttp3.OkHttpClient

interface IOkHttpBuilder {
    fun buildHttpClient(): OkHttpClient
}
