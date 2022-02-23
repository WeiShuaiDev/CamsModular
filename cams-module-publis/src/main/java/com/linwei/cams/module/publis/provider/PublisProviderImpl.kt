package com.linwei.cams.module.publis.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.provider.ProjectProvider
import com.linwei.cams.service.publis.PublisRouterTable
import com.linwei.cams.service.publis.provider.PublisProvider

@Route(path = PublisRouterTable.PATH_SERVICE_PUBLIS)
class PublisProviderImpl : PublisProvider {
    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }
}