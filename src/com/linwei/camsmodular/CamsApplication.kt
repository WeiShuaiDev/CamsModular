package com.linwei.camsmodular

import android.app.Application
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.configuration.ApiConfiguration

class CamsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)
        ARouter.init(this)

        //ApiClient.getInstance(ApiConfiguration.builder().build())
    }
}