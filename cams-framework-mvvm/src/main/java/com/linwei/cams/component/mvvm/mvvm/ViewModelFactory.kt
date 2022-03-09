package com.linwei.cams.component.mvvm.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/8
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构生产 `ViewModel`工厂类，把 `ViewModel` 存储到 Map集合中 `value`，
 *               `Class<in ViewModel>` 存储到 Map 集合中 `key`
 *-----------------------------------------------------------------------
 */
class ViewModelFactory : ViewModelProvider.Factory {

    companion object {

        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(): ViewModelFactory {
            return INSTANCE
                ?: ViewModelFactory().apply {
                    INSTANCE = this
                }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = modelClass.newInstance()
        try {
            return creator as T
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }
}