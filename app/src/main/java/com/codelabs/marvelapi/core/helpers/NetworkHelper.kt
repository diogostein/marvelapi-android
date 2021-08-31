package com.codelabs.marvelapi.core.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkHelper(context: Context) {
    private var connectivityManager: ConnectivityManager? = null

    init {
        connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    }

    @Suppress("DEPRECATION")
    fun isConnectionAvailable(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager?.getNetworkCapabilities(connectivityManager?.activeNetwork)

            return networkCapabilities != null && networkCapabilities.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        } else {
            return when (connectivityManager?.activeNetworkInfo?.type) {
                ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE  -> true
                else -> false
            }
        }
    }

}