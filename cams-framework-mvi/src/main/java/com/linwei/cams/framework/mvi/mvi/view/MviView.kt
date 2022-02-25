package com.linwei.cams.framework.mvi.mvi.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.framework.mvi.mvi.model.MviViewState

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
            it.viewEvent.observe(
                owner
            ) { event ->
                event?.let {
                    bindMviViewEvent(event)
                }
            }

            it.viewState.observe(
                owner
            ) { state ->
                state?.let {
                    bindMviViewState(state)
                }
            }
        }
    }

    /**
     * 绑定 [MviViewEvent] 对象
     * @param event [MviViewEvent]
     */
    private fun bindMviViewEvent(event: MviViewEvent) {
        when (event) {
            is MviViewEvent.ShowSnackBar -> showSnackBar(event.message)
            is MviViewEvent.ShowToast -> showToast(event.message)
            is MviViewEvent.ShowLoadingDialog -> showLoadingDialog(event.message)
            is MviViewEvent.DismissLoadingDialog -> dismissLoadingDialog()
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
    fun showToast(message: String)

    /**
     * 显示加载框
     * @param message [String]
     */
    fun showLoadingDialog(message: String)

    /**
     * 关闭加载框
     */
    fun dismissLoadingDialog()

    /**
     * 绑定 [MviViewState] 对象
     * @param event [MviViewState]
     */
    private fun bindMviViewState(state: MviViewState) {
        bindOtherMviViewState(state)
    }

    /**
     * 绑定其他 [MviViewState] 对象
     * @param event [MviViewState]
     */
    fun bindOtherMviViewState(state: MviViewState) {

    }

}