package com.example.githubdemo.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity

/*
Util class that holds helper functions and constants
Written by Nathan N
 */

class Util {

    companion object {

        fun setFadeAnimation(view: View) {
            var anim = AlphaAnimation(0.0f, 1.0f)
            anim.interpolator= DecelerateInterpolator()
            anim.duration=1000
            view.startAnimation(anim);
        }

         fun hideNavBar(activity: AppCompatActivity) {
            val handler = Handler(Looper.getMainLooper())
            val runnableCode = object : Runnable {
                override fun run() {
                    (activity).supportActionBar?.hide()
                }
            }
            handler.postDelayed(runnableCode, 3500L)
        }

        fun isConnected(context: Context): Boolean {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
                return false
            } else {
                return isConnectedPriorMarshmallow(context)
            }
        }

        fun isConnectedPriorMarshmallow(context: Context): Boolean {
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivity.allNetworkInfo
            for (i in info.indices) if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
            return false
        }

    }

    object Constants {
        const val BASE_URL = "https://api.github.com/"
        const val ISSUES_END_POINT= "repos/reactivex/rxJava/issues"

    }

}
