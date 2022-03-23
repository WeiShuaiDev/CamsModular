package com.linwei.cams.module.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.linwei.cams.component.common.opensource.ARouterManager

class HomeApplication : Application() {
    private lateinit var mHomeApplicationDelegate: HomeAppDelegate

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        mContext = context
        mHomeApplicationDelegate = HomeAppDelegate()
    }

    override fun onCreate() {
        super.onCreate()
        ARouterManager.init(this)
        mHomeApplicationDelegate.onCreate(this)
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

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mHomeApplicationDelegate.onTrimMemory(level)
    }
}