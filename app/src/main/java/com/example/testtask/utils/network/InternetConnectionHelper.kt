package com.example.testtask.utils.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class InternetConnectionHelper(private val connectivityManager: ConnectivityManager) : IInternetConnectionHelper {
    override fun isInternetConnected(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            connectivityManager.activeNetworkInfo?.let { networkInfo ->
                networkInfo.isAvailable && networkInfo.isConnected
            } ?: false
        } else {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let { networkCapabilities ->
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            } ?: false
        }
    }
}
