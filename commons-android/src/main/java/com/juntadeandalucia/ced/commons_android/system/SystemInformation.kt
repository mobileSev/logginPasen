package com.juntadeandalucia.ced.commons_android.system

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build

class SystemInformation (private val context: Context) {

    fun getCurrentVersion(): String = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    fun getPhoneManufacter(): String = Build.MANUFACTURER

    fun getPhoneModel(): String = Build.MODEL

    fun getOperativeSystem(): String = "ANDROID ${Build.VERSION.RELEASE}"

    fun getConnectionType(): String = with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
        val networkInfo: NetworkInfo? = this?.activeNetworkInfo
        return when (networkInfo?.type) {
            ConnectivityManager.TYPE_WIFI -> "WIFI"
            ConnectivityManager.TYPE_MOBILE -> "3G"
            else -> "NOT_CONNECTED"
        }
    }
}