package com.linwei.cams.component.common.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.linwei.cams.component.common.app.ActivityLifecycleCallbacksImpl
import com.linwei.cams.component.common.app.AppBindModuleDelegate
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.ref.SoftReference
import kotlin.system.measureTimeMillis

@HiltAndroidApp
open class CommonBaseApplication : MultiDexApplication() {

    private val mCoroutineScope by lazy { MainScope() }

    private val mLoadModuleProxy by lazy { AppBindModuleDelegate() }

    companion object {
        // 全局Context
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        //全局Application
        @SuppressLint("StaticFieldLeak")
        lateinit var application: CommonBaseApplication
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        context = base
        application = this

        mLoadModuleProxy.onAttachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())

        mLoadModuleProxy.onCreate(this)

        // 策略初始化第三方依赖
        initDepends()
    }

    /**
     * 初始化第三方依赖
     */
    private fun initDepends() {
        // 开启一个 Default Coroutine 进行初始化不会立即使用的第三方
        mCoroutineScope.launch(Dispatchers.Default) {
            mLoadModuleProxy.initByBackstage()
        }

        // 前台初始化
        val allTimeMillis = measureTimeMillis {
            val depends = mLoadModuleProxy.initByFrontDesk()
            var dependInfo: String
            depends.forEach {
                val dependTimeMillis = measureTimeMillis { dependInfo = it() }
                Log.d("BaseApplication", "initDepends: $dependInfo : $dependTimeMillis ms")
            }
        }
        Log.d("BaseApplication", "初始化完成 $allTimeMillis ms")
    }

    override fun onTerminate() {
        super.onTerminate()
        mLoadModuleProxy.onTerminate(this)
        mCoroutineScope.cancel()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mLoadModuleProxy.onLowMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mLoadModuleProxy.onTrimMemory(level)
    }

}