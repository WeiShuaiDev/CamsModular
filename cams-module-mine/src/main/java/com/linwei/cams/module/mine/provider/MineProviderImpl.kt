package com.linwei.cams.module.mine.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.service.mine.MineRouterTable
import com.linwei.cams.service.mine.provider.MineProvider

@Route(path = MineRouterTable.PATH_SERVICE_MINE)
class MineProviderImpl : MineProvider {
    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }
}