package com.linwei.cams.component.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
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
        //在新版本的Fragment#onActivityCreated()废除.使用LifecycleObserver监听
        //onActivityCreated() 方法现已弃用。与 Fragment 视图有关的代码应在 onViewCreated()（在 onActivityCreated() 之前调用）中执行，而其他初始化代码应在 onCreate() 中执行。如需专门在 Activity 的 onCreate() 完成时接收回调，应在 onAttach() 中的 Activity 的 Lifecycle 上注册 LifeCycleObserver，并在收到 onCreate() 回调后将其移除。
        //版权声明：本文为CSDN博主「Ym Android开发工程师」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        requireActivity().lifecycle.addObserver(object: DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                onActivityCreated()
                owner.lifecycle.removeObserver(this)
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        routerInjectBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewExpand(inflater, container, savedInstanceState)
        hasViewBinding().takeIf { true }.apply {
            return viewBindingLogic(inflater, container) ?: inflater.inflate(
                getRootLayoutId(),
                container,
                false
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedExpand(view, savedInstanceState)
        initView()
        initData()
        initEvent()
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
    protected open fun getRootLayoutId(): Int = -1

    protected open fun hasInjectARouter(): Boolean = false

    protected open fun hasViewBinding(): Boolean = true

    protected open fun onCreateViewExpand(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
    }

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun initEvent()

    /**
     * [Fragment]宿主[Activity]初始化结束调用
     */
    protected open fun onActivityCreated(){

    }

    protected open fun onViewCreatedExpand(view: View, savedInstanceState: Bundle?) {
    }
}