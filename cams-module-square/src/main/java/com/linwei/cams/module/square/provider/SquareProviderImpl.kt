package com.linwei.cams.module.square.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.provider.ProjectProvider
import com.linwei.cams.service.square.SquareRouterTable
import com.linwei.cams.service.square.provider.SquareProvider

@Route(path = SquareRouterTable.PATH_SERVICE_SQUARE)
class SquareProviderImpl : SquareProvider {
    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }
}