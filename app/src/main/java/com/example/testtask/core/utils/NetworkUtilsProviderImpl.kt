package com.example.testtask.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.testtask.data.handler.NetworkUtilsProvider

class NetworkUtilsProviderImpl(
    private val context: Context
) : NetworkUtilsProvider {
    override fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        fun isOldApiNetworkAvailable(): Boolean {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val netInfo = cm.activeNetwork
            val capabilities = cm.getNetworkCapabilities(netInfo)
            if (netInfo != null && capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) {
                return true
            }
        }
        return isOldApiNetworkAvailable()
    }
}
