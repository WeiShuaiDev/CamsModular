package com.linwei.camsmodular

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.linwei.cams.component.common.opensource.ARouterManager
import com.linwei.cams.module.home.HomeApplicationDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CamsApplication : MultiDexApplication() {

    private val mHomeApplicationDelegate by lazy {
        HomeApplicationDelegate()
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        mHomeApplicationDelegate.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        ARouterManager.init(this)

        mHomeApplicationDelegate.onCreate(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mHomeApplicationDelegate.onTrimMemory(level)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouterManager.destroy()
        mHomeApplicationDelegate.onTerminate(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mHomeApplicationDelegate.onLowMemory(this)
    }
}