package com.linwei.cams.component.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.opensource.ARouterManager
import com.trello.rxlifecycle4.components.support.RxFragment
import com.trello.rxlifecycle4.components.support.RxFragmentActivity
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * 基类CommonBaseFragment
 */
abstract class CommonBaseFragment<VB : ViewBinding> : RxFragment() {

    protected lateinit var mViewBinding: VB

    protected lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
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
        onViewCreatedExpand(view, savedInstanceState)
        initView()
        initData()
        initEvent()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        routerInjectBinding()
    }

    /**
     * ViewBinding绑定逻辑
     */
    @Suppress("UNCHECKED_CAST")
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
            mViewBinding = inflate.invoke(null, inflater, container, false) as VB
            return mViewBinding.root
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return null
    }

    private fun routerInjectBinding() {
        hasInjectARouter().takeIf { true }.apply {
            activity?.let {
                ARouterManager.inject(it)
            }
        }
    }

    protected open fun hasInjectARouter(): Boolean = false

    protected open fun getRootLayoutId(): Int = -1

    protected open fun onCreateViewExpand(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
    }

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun initEvent()

    protected open fun onViewCreatedExpand(view: View, savedInstanceState: Bundle?) {
    }
}