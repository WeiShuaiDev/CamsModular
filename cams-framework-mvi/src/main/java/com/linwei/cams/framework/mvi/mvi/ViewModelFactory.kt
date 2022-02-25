package com.linwei.cams.framework.mvi.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVI架构生产 `ViewModel`工厂类，把 `ViewModel` 存储到 Map集合中 `value`，
 *               `Class<in ViewModel>` 存储到 Map 集合中 `key`
 *-----------------------------------------------------------------------
 */
class ViewModelFactory : ViewModelProvider.Factory {

    private val mCreators: Map<Class<out ViewModel>, ViewModel> = mutableMapOf()

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
        val creator = mCreators[modelClass] ?: mCreators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            return creator as T
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }
}