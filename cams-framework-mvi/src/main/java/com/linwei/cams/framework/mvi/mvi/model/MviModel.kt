package com.linwei.cams.framework.mvi.mvi.model

import com.linwei.cams.framework.mvi.ext.FetchStatus

data class MviViewState(val status: FetchStatus)

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVI架构  `Model` 模块
 *-----------------------------------------------------------------------
 */
sealed class MviViewEvent {
    /**
     * 显示 SnackBar
     * @param message 内容
     */
    data class ShowSnackBar(val message: String) : MviViewEvent()

    /**
     * 显示 Toast
     * @param message 内容
     */
    data class ShowToast(val message: String) : MviViewEvent()

    /**
     * 显示加载框
     * @param message 内容
     */
    data class ShowLoadingDialog(val message: String) : MviViewEvent()

    /**
     * 关闭加载框
     */
    object DismissLoadingDialog : MviViewEvent()

}
