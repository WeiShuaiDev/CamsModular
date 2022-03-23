package com.linwei.cams.component.web

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.auto.service.AutoService
import com.linwei.cams.component.common.app.AppDelegate
import com.linwei.cams.component.common.utils.ProcessUtils

@AutoService(AppDelegate::class)
class WebAppDelegate:AppDelegate {

    private lateinit var mContext:Context

    private  lateinit var mApplication:Application

    /**
     * 同[Application.attachBaseContext]
     * @param context [Context]
     */
    override fun onAttachBaseContext(context: Context) {
        this.mContext=context
    }

    /**
     * 同[Application.onCreate]
     * @param application [Application]
     */
    override fun onCreate(application: Application) {
        this.mApplication=application
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
        }
        return list
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {
        initX5WebViewCore()
    }

    /**
     * 腾讯TBS WebView X5 内核初始化
     */
    private fun initX5WebViewCore() {
        // dex2oat优化方案
//        val map = HashMap<String, Any>()
//        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
//        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
//        QbSdk.initTbsSettings(map)
//
//        // 允许使用非wifi网络进行下载
//        QbSdk.setDownloadWithoutWifi(true)
//
//        // 初始化
//        QbSdk.initX5Environment(BaseApplication.context, object : PreInitCallback {
//
//            override fun onCoreInitFinished() {
//                Log.d("ApplicationInit", " TBS X5 init finished")
//            }
//
//            override fun onViewInitFinished(p0: Boolean) {
//                // 初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核
//                Log.d("ApplicationInit", " TBS X5 init is $p0")
//            }
//        })
    }

}