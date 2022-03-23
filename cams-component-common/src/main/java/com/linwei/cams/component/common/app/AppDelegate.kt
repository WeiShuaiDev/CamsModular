package com.linwei.cams.component.common.app

import android.app.Application
import android.content.Context

interface AppDelegate {

    /**
     * 同[Application.attachBaseContext]
     * @param context [Context]
     */
    fun onAttachBaseContext(context: Context)

    /**
     * 同[Application.onCreate]
     * @param application [Application]
     */
    fun onCreate(application: Application)

    /**
     * 同[Application.onTerminate]
     * @param application [Application]
     */
    fun onTerminate(application: Application)

    /**
     * 同[Application.onLowMemory]低内存时执行
     * @param application [Application]
     */
    fun onLowMemory(application: Application)

    /**
     * 同[Application.onTrimMemory]清理内存时执行
     * @param level [Int]
     */
    fun onTrimMemory(level: Int)

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    fun initByFrontDesk(): MutableList<() -> String>

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    fun initByBackstage()
}