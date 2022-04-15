package com.linwei.cams.module.publis.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.module.publis.http.ApiService
import com.linwei.cams.service.publis.PublisRouterTable
import com.linwei.cams.service.publis.provider.PublisProvider
import javax.inject.Inject

@Route(path = PublisRouterTable.PATH_SERVICE_PUBLIS)
class PublisProviderImpl @Inject constructor(private val apiService: ApiService) : PublisProvider {

    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }
}