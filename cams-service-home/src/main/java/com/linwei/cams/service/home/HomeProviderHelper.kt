package com.linwei.cams.service.home

import com.alibaba.android.arouter.launcher.ARouter

object HomeProviderHelper {
    /**
     * HomeProvider
     */
    fun getNewHouseProvider(): HomeProvider =
        ARouter.getInstance().build(HomeRouterTable.PATH_SERVICE_HOME).navigation() as HomeProvider
}