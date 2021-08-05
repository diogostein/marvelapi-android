package com.codelabs.marvelapi.core.api.interceptors

import com.codelabs.marvelapi.BuildConfig
import com.codelabs.marvelapi.core.utils.DigestUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val timestamp = Date().time
        val hash = DigestUtils.md5(
            "$timestamp${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}"
        )

        val url = request
            .url
            .newBuilder()
            .addQueryParameter("ts", timestamp.toString())
            .addQueryParameter("apikey", BuildConfig.PUBLIC_API_KEY)
            .addQueryParameter("hash", hash)
            .build()

        return chain.proceed(request.newBuilder().url(url).build())
    }
}