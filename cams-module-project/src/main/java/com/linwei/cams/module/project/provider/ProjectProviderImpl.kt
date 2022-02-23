package com.linwei.cams.module.project.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.provider.ProjectProvider

@Route(path = ProjectRouterTable.PATH_SERVICE_PROJECT)
class ProjectProviderImpl : ProjectProvider {
    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }
}