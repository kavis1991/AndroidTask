package com.kavis.androidtask.data.remote

import com.kavis.androidtask.APP_ID
import okhttp3.Interceptor

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain) = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("app-id", APP_ID)
                .build()
        )
    }
}