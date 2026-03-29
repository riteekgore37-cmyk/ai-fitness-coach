package com.aifitnesscoach.android.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val token = tokenProvider()
        val url = request.url.toString()

        // Only add Authorization header to requests going to our backend
        val isBackendRequest = url.contains("ai-fitness-coach-backend-an8o.onrender.com")

        val newRequest = if (!token.isNullOrEmpty() && isBackendRequest) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }

        return chain.proceed(newRequest)
    }
}