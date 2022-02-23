package com.linwei.cams.component.common.opensource

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.component.common.BuildConfig

object ARouterManager {

    /**
     * 初始化 ARouter
     */
    fun init(app: Application) {
        ///初始化路由
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(app)

    }

    /**
     * 销毁 ARouter
     */
    fun destroy() {
        ARouter.getInstance().destroy()
    }

    /**
     * 注入 ARouter 依赖
     */
    fun inject(target: Any) {
        ARouter.getInstance().inject(target)
    }

    /**
     * ARouter Inject 逻辑处理
     * @param activity Activity
     */
    fun routerInjectBinding(activity: Activity) {
        if (activity is ARouterInjectable) {
            ARouter.getInstance().inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        savedInstanceState: Bundle?
                    ) {
                        if (f is ARouterInjectable) {
                            ///注入ARouter参数
                            ARouter.getInstance().inject(f)
                        }
                    }
                }, true
            )
        }
    }
}