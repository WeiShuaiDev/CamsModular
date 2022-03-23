package com.linwei.cams.framework.mvi.mvi.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.linwei.cams.framework.mvi.ktx.observeEvent
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import com.linwei.cams.framework.mvi.mvi.intent.StatusCode
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVI架构  `View` 模块
 *-----------------------------------------------------------------------
 */
interface MviView<VM : MviViewModel> {

    /**
     * 获取 [ViewModel] 对象
     * @return VM [ViewModel]
     */
    fun createViewModel(): VM?

    /**
     * 绑定 [ViewModel] 对象
     * @param VM [viewModel]
     * @param owner [LifecycleOwner]
     */
    fun bindViewModel(viewModel: VM?, owner: LifecycleOwner) {
        viewModel?.let {
            it.viewEvent.observeEvent(
                owner
            ) { event ->
                bindMviViewEvent(event)
            }
        }
    }

    /**
     * 绑定 [MviViewEvent] 对象
     * @param event [MviViewEvent]
     */
    private fun bindMviViewEvent(event: MviViewEvent) {
        when (event.code) {
            StatusCode.SHOW_SNACK_BAR -> {
                showSnackBar(event.content.toString())
            }
            StatusCode.SHOW_TOAST -> {
                showToast(event.content.toString())
            }
            StatusCode.SHOW_LOADING_DIALOG -> {
                showLoadingDialog(event.content.toString())
            }
            StatusCode.DISMISS_LOADING_DIALOG -> {
                dismissLoadingDialog()
            }
            else -> {
                bindOtherMviViewEvent(event)
            }
        }
    }

    /**
     * 绑定其他 [MviViewEvent] 对象
     * @param event [MviViewEvent]
     */
    fun bindOtherMviViewEvent(event: MviViewEvent) {
    }

    /**
     * 显示 SnackBar
     * @param message [String]
     */
    fun showSnackBar(message: String)

    /**
     * 显示Toast
     * @param message [String]
     */
    fun showToast(message: String?)

    /**
     * 显示加载框
     * @param message [String]
     */
    fun showLoadingDialog(message: String)

    /**
     * 关闭加载框
     */
    fun dismissLoadingDialog()

}