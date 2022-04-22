package com.linwei.cams.service.home.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.service.home.HomeRouterTable

object HomeProviderHelper {
    /**
     * HomeProvider
     */
    fun getHomeProvider(): HomeProvider? =
        ARouter.getInstance().build(HomeRouterTable.PATH_SERVICE_HOME).navigation() as HomeProvider?
}