package com.vfgodoy.android_system_bar.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.service.constants.Error.CODES.WRONG_PASSWORD

class Util {
    companion object{

        fun makeToast(context : Context, message : String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun isConnectionAvailable(context: Context) : Boolean {
            val connection =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connection != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val networkCapabilities = connection.getNetworkCapabilities(connection.activeNetwork)
                    if (networkCapabilities != null) {
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            return true
                        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            return true
                        }
                        return false
                    }
                } else {
                    //TODO: RESOLVE DEPRECATED
                    val info = connection.activeNetworkInfo
                    return info != null && info.isConnected
                }
            }
            return false
        }

    }
}