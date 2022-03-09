package com.linwei.cams.component.mvvm.mvvm

import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/8
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构  `ViewModel` 代理类
 *-----------------------------------------------------------------------
 */
 interface ViewModelDelegate<VM : MvvmViewModel> {

    /**
     *ViewModelFactory
     */
    val mViewModelFactory: ViewModelFactory
        get() = ViewModelFactory.getInstance()

    /**
     * 获取对应 `ViewModel` 的 Class 类
     * @return Class<VM> [Class]
     */
    @Suppress("UNCHECKED_CAST")
     fun fetchVMClass(): Class<VM> {

        var cls: Class<*>? = javaClass
        var vmClass: Class<VM>? = null
        while (vmClass == null && cls != null) {
            vmClass = fetchVMClass(cls)
            cls = cls.superclass
        }
        if (vmClass == null) {
            vmClass = MvvmViewModel::class.java as Class<VM>
        }
        return vmClass
    }

    /**
     * 获取对应 `ViewModel` 的 Class 类
     * @param cls [Class]
     * @return Class<VM> [Class]
     */
    @Suppress("UNCHECKED_CAST")
     fun fetchVMClass(cls: Class<*>): Class<VM>? {
        val type: Type? = cls.genericSuperclass

        if (type is ParameterizedType) {
            val types: Array<out Type> = type.actualTypeArguments
            for (t: Type in types) {
                if (t is Class<*>) {
                    if (MvvmViewModel::class.java.isAssignableFrom(t)) {
                        return t as Class<VM>
                    }
                } else if (t is ParameterizedType) {
                    val rawType = t.rawType
                    if (rawType is Class<*>) {
                        if (MvvmViewModel::class.java.isAssignableFrom(rawType)) {
                            return rawType as Class<VM>
                        }
                    }
                }
            }
        }
        return null
    }
}