package com.linwei.cams.module.project

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.linwei.cams.component.common.base.AppDelegate

class ProjectApplicationDelegate : AppDelegate {
    override fun attachBaseContext(context: Context) {
    }

    override fun onCreate(application: Application) {

        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })

    }

    override fun onTerminate(application: Application) {
    }

    override fun onLowMemory(application: Application) {
    }

    override fun onTrimMemory(level: Int) {
    }
}