package com.linwei.cams.component.network

import com.linwei.cams.component.network.configuration.ApiConfiguration
import com.linwei.cams.component.network.converter.FastJsonConverterFactory
import com.linwei.cams.component.network.intercepter.SignInterceptor
import com.linwei.cams.component.network.service.ServiceWrap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import java.util.concurrent.TimeUnit

class ApiClient private constructor(var apiConfiguration: ApiConfiguration) {

    /**
     * OkHttpClient
     */
    private var mOkHttpClient: OkHttpClient

    private val mApiServiceMap: MutableMap<String, Any> = mutableMapOf()

    companion object {
        private var INSTANCE: ApiClient? = null

        /**
         * 链接时间
         */
        private val CONNECT_TIME_OUT: Long = 60

        /**
         * 读取时间
         */
        private val READ_TIME_OUT: Long = 60

        /**
         * 写入时间
         */
        private val WHITE_TIME_OUT: Long = 60

        @JvmStatic
        fun getInstance(apiConfiguration: ApiConfiguration): ApiClient {
            return INSTANCE ?: ApiClient(apiConfiguration).apply {
                INSTANCE = this
            }
        }
    }

    init {
        val builder = OkHttpClient().newBuilder()
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(WHITE_TIME_OUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }
        builder.addNetworkInterceptor(SignInterceptor())
        this.mOkHttpClient = builder.build()
    }

    /**
     * 获取Service Api
     */
    fun <T> getService(proxy: ServiceWrap<T>): T? {
        val host = proxy.getHost()
        val serviceClass: Class<T> = proxy.fetchRealService()

        val key = proxy.getModuleName() + "&" + proxy.getIdentify()

        if (mApiServiceMap.containsKey(key)) {
            mApiServiceMap.get(key)?.let {
                if (serviceClass.isInstance(it)) {
                    return serviceClass.cast(it)
                }
            }
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(FastJsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(mOkHttpClient)
            .build()

        return retrofit.create(serviceClass)?.apply {
            mApiServiceMap.put(key, this)
        }
    }
}