package com.linwei.cams.component.common.utils

import android.os.Build
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.snackbar.Snackbar
import com.linwei.cams.component.common.base.CommonBaseApplication
import com.linwei.cams.component.common.ktx.snackBar

/**************************************************************************************************/

/**
 * 发送普通EventBus事件
 */
fun sendEvent(event: Any) = EventBusUtils.postEvent(event)

/**************************************************************************************************/
/**
 * 阿里路由不带参数跳转
 * @param routerUrl String 路由地址
 */
fun aRouterJump(routerUrl: String) {
    ARouter.getInstance().build(routerUrl).navigation()
}

/**************************************************************************************************/
/**
 * toast
 * @param msg String 文案
 * @param duration Int 时间
 */
fun toast(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (msg?.isNotEmpty() == true) {
        ToastUtils.showToast(msg, duration)
    }
}

/**
 * toast
 * @param msgId Int String资源ID
 * @param duration Int 时间
 */
fun toast(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtils.showToast(msgId, duration)
}

/**************************************************************************************************/
/**
 * snackbar
 * @param view View
 * @param message String
 * @param duration Int 时间
 */
fun snackBar(view:View,message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, length)
        .show()
}

/**************************************************************************************************/
/**
 * 获取App版本号
 * @return Long App版本号
 */
fun getVersionCode(): Long {
    val packageInfo = CommonBaseApplication.context
        .packageManager
        .getPackageInfo(CommonBaseApplication.context.packageName, 0)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        packageInfo.versionCode.toLong()
    }
}

/**************************************************************************************************/
/**
 * 获取App版本名
 * @return String App版本名
 */
fun getVersionName(): String {
    return CommonBaseApplication.context
        .packageManager
        .getPackageInfo(CommonBaseApplication.context.packageName, 0)
        .versionName
}