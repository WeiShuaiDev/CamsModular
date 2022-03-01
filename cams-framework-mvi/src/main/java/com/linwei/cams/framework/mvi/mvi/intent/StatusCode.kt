package com.linwei.cams.framework.mvi.mvi.intent

import androidx.annotation.IntDef
import com.linwei.cams.framework.mvi.mvi.intent.StatusCode.Companion.DISMISS_LOADING_DIALOG
import com.linwei.cams.framework.mvi.mvi.intent.StatusCode.Companion.SHOW_LOADING_DIALOG
import com.linwei.cams.framework.mvi.mvi.intent.StatusCode.Companion.SHOW_SNACK_BAR
import com.linwei.cams.framework.mvi.mvi.intent.StatusCode.Companion.SHOW_TOAST

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/28
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 定义四种网络请求状态，[SHOW_SNACK_BAR] 显示SnackBar；[SHOW_TOAST] 显示Toast; [SHOW_LOADING_DIALOG] 显示Dialog;
 * [DISMISS_LOADING_DIALOG] 关闭Dialog;
 *-----------------------------------------------------------------------
 */

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@IntDef(SHOW_SNACK_BAR, SHOW_TOAST, SHOW_LOADING_DIALOG, DISMISS_LOADING_DIALOG)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class StatusCode {
    companion object {
        const val SHOW_SNACK_BAR: Int = 1
        const val SHOW_TOAST: Int = 2
        const val SHOW_LOADING_DIALOG: Int = 3
        const val DISMISS_LOADING_DIALOG: Int = 4
    }
}
