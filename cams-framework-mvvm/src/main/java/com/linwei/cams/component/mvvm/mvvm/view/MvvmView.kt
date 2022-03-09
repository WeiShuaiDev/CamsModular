package com.linwei.cams.component.mvvm.mvvm.view

import androidx.lifecycle.ViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/8
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构  `View` 接口定义
 *-----------------------------------------------------------------------
 */
interface MvvmView<VM> {

    /**
     * 获取 [ViewModel] 对象
     * @return VM [ViewModel]
     */
    fun createViewModel(): VM?

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