package com.linwei.camsmodular

import android.app.Application
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter

class CamsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)
        ARouter.init(this)
    }
}