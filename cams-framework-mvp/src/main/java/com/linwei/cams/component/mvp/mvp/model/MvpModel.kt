package com.linwei.cams.component.mvp.mvp.model

import androidx.lifecycle.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/4
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构  `Model` 接口实现
 * -----------------------------------------------------------------------
 */
open class MvpModel:IMvpModel, DefaultLifecycleObserver {

    override fun onDestroy() {

    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        owner.lifecycle.removeObserver(this)
    }
}