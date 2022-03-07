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
     * 显示加载框
     * @param message [Int]
     */
    fun showLoading(message: Int)

    /**
     * 隐藏加载框
     */
    fun hideLoading()

    /**
     * 显示日志信息
     * @param message [Int]
     */
    fun showMessage(message: Int)

    /**
     * 显示日志信息
     * @param message [String]
     */
    fun showMessage(message: String)

}