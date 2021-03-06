package com.vfgodoy.android_system_bar.util

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.vfgodoy.android_system_bar.R

class Util {
    companion object{

        fun makeToast(context : Context?, message : String){
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
                    //TODO: RESOLVER DEPRECATED
                    val info = connection.activeNetworkInfo
                    return info != null && info.isConnected
                }
            }
            return false
        }

        fun confirmDialogAlert(context: Context, title : String, message : String, callback : () -> Unit ){
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.yes) { dialog, which ->
                    callback()
                }
                .setNeutralButton(R.string.no, null)
                .show()
        }

    }
}