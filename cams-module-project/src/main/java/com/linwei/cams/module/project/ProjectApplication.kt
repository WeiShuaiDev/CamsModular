package com.linwei.cams.module.project

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.linwei.cams.component.common.opensource.ARouterManager

class ProjectApplication : Application() {
    private lateinit var mProjectApplicationDelegate: ProjectAppDelegate

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        mContext = context
        mProjectApplicationDelegate = ProjectAppDelegate()
    }

    override fun onCreate() {
        super.onCreate()
        ARouterManager.init(this)
        mProjectApplicationDelegate.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouterManager.destroy()
        mProjectApplicationDelegate.onTerminate(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mProjectApplicationDelegate.onLowMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mProjectApplicationDelegate.onTrimMemory(level)
    }
}