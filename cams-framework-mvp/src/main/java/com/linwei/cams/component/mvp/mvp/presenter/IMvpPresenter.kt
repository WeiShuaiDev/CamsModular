package com.linwei.cams.component.mvp.mvp.presenter

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/4
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构 `Presenter` 接口定义
 *-----------------------------------------------------------------------
 */
interface IMvpPresenter {
    /**
     * 初始化操作
     */
    fun onStart()

    /**
     * 在框架 `MvpBaseActivity#onDestroy()` 方法，会默认调用 `IPresenter#onDestroy()`
     */
    fun onDestroy()
}