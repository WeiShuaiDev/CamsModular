package com.linwei.cams.component.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.opensource.ARouterManager
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * 基类CommonBaseActivity
 */
abstract class CommonBaseActivity<T : ViewBinding> : AppCompatActivity() {

    protected lateinit var mViewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        onBeforeCreateExpand(savedInstanceState)
        super.onCreate(savedInstanceState)
        onCreateExpand()

        routerInjectBinding()

        viewBindingLogic().takeIf { true }?.apply {
            setContentView(this)
        } ?: setContentView(getRootLayoutId())

        initView()
        initData()
        initEvent()
    }

    /**
     * ViewBinding绑定逻辑
     */
    private fun viewBindingLogic(): View? {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        try {
            val cls = type.actualTypeArguments[0] as Class<*>
            val inflate = cls.getDeclaredMethod("inflate", LayoutInflater::class.java)
            mViewBinding = inflate.invoke(null, layoutInflater) as T
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
            ARouterManager.inject(this@CommonBaseActivity)
        }
    }

    protected open fun getRootLayoutId(): Int = -1

    protected open fun hasInjectARouter(): Boolean = false

    protected open fun onBeforeCreateExpand(savedInstanceState: Bundle?) {
    }

    protected open fun onCreateExpand() {}

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun initEvent()

}