package com.arubianoch.posttest.data.network.connectivity

import android.content.Context
import android.net.ConnectivityManager
import com.arubianoch.posttest.internal.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Andres Rubiano Del Chiaro
 */
class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}