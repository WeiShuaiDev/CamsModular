package com.linwei.cams.service.publis.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.publis.PublisRouterTable

object PublisProviderHelper {
    /**
     * PublisProvider
     */
    fun getPublisProvider(): PublisProvider? =
        ARouter.getInstance().build(PublisRouterTable.PATH_SERVICE_PUBLIS).navigation() as PublisProvider?
}