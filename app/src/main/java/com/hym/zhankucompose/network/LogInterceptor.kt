package com.hym.zhankucompose.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.atomic.AtomicLong

class LogInterceptor : Interceptor {
    companion object {
        private const val TAG = "LogInterceptor"
    }

    private val mCount = AtomicLong()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val count = mCount.incrementAndGet()
        val request = chain.request()
        Log.d(TAG, "[$count] >>>>>> $request")
        val response = chain.proceed(request)
        Log.d(TAG, "[$count] <<<<<< $response contentLength=${response.body?.contentLength()}")
        return response
    }
}