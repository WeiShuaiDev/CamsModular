package com.linwei.cams.component.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType


/**
 * 基类CommonBaseFragment
 */
abstract class CommonBaseFragment<T : ViewBinding> : Fragment() {

    protected lateinit var mViewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewExpand(inflater, container, savedInstanceState)
        return viewBindingLogic(inflater, container) ?: inflater.inflate(
            getRootLayoutId(),
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initEvent()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        routerBindingLogic()
    }

    /**
     * ViewBinding绑定逻辑
     */
    private fun viewBindingLogic(inflater: LayoutInflater, container: ViewGroup?): View? {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        try {
            val cls = type.actualTypeArguments[0] as Class<*>
            val inflate = cls.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.javaPrimitiveType
            )
            mViewBinding = inflate.invoke(null, inflater, container, false) as T
            return mViewBinding.getRoot()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return null
    }

    private fun routerBindingLogic() {
        hasARouter().takeIf { true }?.apply {
            ARouter.getInstance().inject(this)
        }
    }

    protected open fun hasARouter(): Boolean = false

    protected open fun getRootLayoutId(): Int = -1

    protected open fun onCreateViewExpand(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
    }

    protected abstract fun initEvent()

    protected abstract fun initData()

    protected abstract fun initView()
}