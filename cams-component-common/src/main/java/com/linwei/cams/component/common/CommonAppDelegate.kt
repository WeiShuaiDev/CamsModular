package com.linwei.cams.component.common

import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import com.linwei.cams.component.common.app.AppDelegate
import com.linwei.cams.component.common.opensource.ARouterManager
import com.linwei.cams.component.common.utils.ProcessUtils
import com.tencent.bugly.crashreport.CrashReport

@AutoService(AppDelegate::class)
class CommonAppDelegate : AppDelegate {

    private lateinit var mContext: Context

    private lateinit var mApplication: Application

    /**
     * 同[Application.attachBaseContext]
     * @param context [Context]
     */
    override fun onAttachBaseContext(context: Context) {
        this.mContext = context
    }

    /**
     * 同[Application.onCreate]
     * @param application [Application]
     */
    override fun onCreate(application: Application) {
        this.mApplication = application
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
        val list = mutableListOf<() -> String>()
        // 以下只需要在主进程当中初始化 按需要调整
        if (ProcessUtils.isMainProcess(mContext)) {
            list.add { initARouter() }
        }
        list.add { initTencentBugly() }
        return list
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {
    }

    /**
     * 阿里路由 ARouter 初始化
     */
    private fun initARouter(): String {
        ARouterManager.init(mApplication)
        return "ARouter -->> init complete"
    }

    /**
     * 初始化 腾讯Bugly
     * 测试环境应该与正式环境的日志收集渠道分隔开
     * 目前有两个渠道 测试版本/正式版本
     */
    private fun initTencentBugly(): String {
        // 第三个参数为SDK调试模式开关
        CrashReport.initCrashReport(
            mContext,
            "",
            BuildConfig.DEBUG
        )
        return "Bugly -->> init complete"
    }
}