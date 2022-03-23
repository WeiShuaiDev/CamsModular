package com.linwei.cams.module.project

import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import com.linwei.cams.component.common.app.AppDelegate

@AutoService(AppDelegate::class)
class ProjectAppDelegate : AppDelegate {
    /**
     * 同[Application.attachBaseContext]
     * @param context [Context]
     */
    override fun onAttachBaseContext(context: Context) {
    }

    /**
     * 同[Application.onCreate]
     * @param application [Application]
     */
    override fun onCreate(application: Application) {
    }

    /**
     * 同[Application.onTerminate]
     * @param application [Application]
     */
    override fun onTerminate(application: Application) {
    }

    /**
     * 同[Application.onLowMemory]低内存时执行
     * @param application [Application]
     */
    override fun onLowMemory(application: Application) {
    }

    /**
     * 同[Application.onTrimMemory]清理内存时执行
     * @param level [Int]
     */
    override fun onTrimMemory(level: Int) {
    }

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    override fun initByFrontDesk(): MutableList<() -> String> {
        return mutableListOf()
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {
    }
}