package com.linwei.cams.module.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter

class HomeApplication : Application() {
    private lateinit var mHomeApplicationDelegate: HomeApplicationDelegate

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        mContext = context
        mHomeApplicationDelegate = HomeApplicationDelegate()
    }

    override fun onCreate() {
        super.onCreate()
        mHomeApplicationDelegate.onCreate(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
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