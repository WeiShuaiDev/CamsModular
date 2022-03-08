package com.linwei.cams.component.mvp.mvp.view

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构  `View` 接口定义
 *-----------------------------------------------------------------------
 */
interface IMvpView {

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