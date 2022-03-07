package com.linwei.cams.component.mvp.mvp.model

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/4
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构  `Model` 接口定义
 *-----------------------------------------------------------------------
 */
interface IMvpModel {
    /**
     * 在框架 `BaseActivity#onDestroy()` 方法，会默认调用 `IModel#onDestroy()`
     */
    fun onDestroy()
}