package com.example.vftask.utils.systemmanger

import android.content.Context

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


internal class SystemMangerImpl(
    private val context: Context
) : SystemManger {

    override fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val activeNetworkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        || activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }


}