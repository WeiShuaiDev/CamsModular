package com.linwei.cams.service.square.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.square.SquareRouterTable

object SquareProviderHelper {
    /**
     * SquareProvider
     */
    fun getSquareProvider(): SquareProvider? =
        ARouter.getInstance().build(SquareRouterTable.PATH_SERVICE_SQUARE).navigation() as SquareProvider?
}