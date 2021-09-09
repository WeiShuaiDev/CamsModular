package com.linwei.cams.component.network.intercepter

import okhttp3.Interceptor
import okhttp3.Response

class SignInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}