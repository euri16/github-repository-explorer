package dev.eury.core.network.interceptors

import dev.eury.core.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val AUTH_HEADER_NAME = "Authorization"
private const val ACCEPT_HEADER_NAME = "Accept"
private const val ACCEPT_HEADER_VALUE = "application/vnd.github+json"
private const val VERSION_HEADER_NAME = "X-GitHub-Api-Version"

class HeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain
            .request()
            .newBuilder()

        requestBuilder.addHeader(AUTH_HEADER_NAME, "Bearer ${BuildConfig.API_AUTH_TOKEN}")
        requestBuilder.addHeader(ACCEPT_HEADER_NAME, ACCEPT_HEADER_VALUE)
        requestBuilder.addHeader(VERSION_HEADER_NAME, BuildConfig.API_VERSION)

        return chain.proceed(requestBuilder.build())
    }
}