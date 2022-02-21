package com.linwei.cams.component.common.base

import android.app.Application
import android.content.Context

interface AppDelegate {

    /**
     * @param context [Context]
     */
    fun attachBaseContext(context: Context)

    /**
     * 初始化
     * @param application [Application]
     */
    fun onCreate(application: Application)

    /**
     * 回收资源时执行
     * @param application [Application]
     */
    fun onTerminate(application: Application)

    /**
     * 低内存时执行
     * @param application [Application]
     */
    fun onLowMemory(application: Application)

    /**
     * 清理内存时执行
     * @param level [Int]
     */
    fun onTrimMemory(level: Int)
}