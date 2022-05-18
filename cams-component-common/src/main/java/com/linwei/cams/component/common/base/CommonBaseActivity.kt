package com.linwei.cams.component.common.base

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.opensource.ARouterManager
import com.linwei.cams.component.common.utils.AndroidBugFixUtils
import com.linwei.cams.component.common.utils.EventBusUtils
import com.quyunshuo.androidbaseframemvvm.base.utils.status.ViewStatusHelper
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import me.jessyan.autosize.AutoSizeCompat
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * 基类CommonBaseActivity
 */
abstract class CommonBaseActivity<VB : ViewBinding> : RxAppCompatActivity() {

    protected lateinit var mContext: Context

    protected lateinit var mViewBinding: VB

    protected var mBaseStatusHelper: ViewStatusHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        onBeforeCreateExpand(savedInstanceState)
        super.onCreate(savedInstanceState)

        routerInjectBinding()
        eventBusBinding()
        viewBinding()

        //注册状态帮助类
        mBaseStatusHelper = onRegisterStatusHelper()
        //恢复状态数据
        mBaseStatusHelper?.onRestoreInstanceStatus(savedInstanceState)

        onCreateExpand()

        initView()
        initData()
        initEvent()
    }


    /**
     * ViewBinding绑定逻辑
     */
    @Suppress("UNCHECKED_CAST")
    private fun viewBindingLogic(): View? {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        try {
            val cls = type.actualTypeArguments[0] as Class<*>
            val inflate = cls.getDeclaredMethod("inflate", LayoutInflater::class.java)
            mViewBinding = inflate.invoke(null, layoutInflater) as VB
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

    /**
     * ViewBinding绑定
     */
    private fun viewBinding() {
        if(hasViewBinding()){
            val viewBindingRoot = viewBindingLogic()
            if (viewBindingRoot != null) {
                setContentView(viewBindingRoot)
                return
            }
        }
        setContentView(getRootLayoutId())
    }

    /**
     * EventBus绑定
     */
    private fun eventBusBinding(){
        hasEventBus().takeIf { it }?.apply{
            EventBusUtils.register(this@CommonBaseActivity)
        }
    }

    /**
     * Router注入绑定
     */
    private fun routerInjectBinding() {
        hasInjectARouter().takeIf { it }?.apply {
            ARouterManager.inject(this@CommonBaseActivity)
        }
    }

    /**
     * 保存状态
     */
    override fun onSaveInstanceState(outState: Bundle) {
        mBaseStatusHelper?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    /**
     * 注册状态管理帮助类,子类重写此方法以注册帮助类。
     * 每一层都有可能有自己的状态管理帮助类，所以继承重写的时候，需要将super的对象传入自己的帮助类构造函数中
     */
    protected open fun onRegisterStatusHelper(): ViewStatusHelper? {
        return null
    }

    protected open fun getRootLayoutId(): Int = -1

    protected open fun hasInjectARouter(): Boolean = false

    protected open fun hasViewBinding(): Boolean = true

    protected open fun hasEventBus():Boolean =false

    protected open fun onBeforeCreateExpand(savedInstanceState: Bundle?) {
    }

    protected open fun onCreateExpand() {
        mContext = this
    }

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun initEvent()

    /**
     *  获取 `ViewBinding` 对象
     *  @return mViewBinding [VB]
     */
    protected fun getViewBinding(): VB = mViewBinding

    override fun getResources(): Resources {
        // 主要是为了解决 AndroidAutoSize 在横屏切换时导致适配失效的问题
        // 但是 AutoSizeCompat.autoConvertDensity() 对线程做了判断 导致Coil等图片加载框架在子线程访问的时候会异常
        // 所以在这里加了线程的判断 如果是非主线程 就取消单独的适配
        val res=super.getResources()
        res.updateConfiguration(Configuration().apply{setToDefaults()},res.displayMetrics)
        if (Looper.myLooper() == Looper.getMainLooper()) {
            AutoSizeCompat.autoConvertDensityOfGlobal((res))
        }
        return super.getResources()
    }

    override fun onDestroy() {
        super.onDestroy()
        hasEventBus().takeIf { it }?.apply{
            EventBusUtils.unRegister(this@CommonBaseActivity)
        }

        // 解决某些特定机型会触发的Android本身的Bug
        AndroidBugFixUtils().fixSoftInputLeaks(this)
    }

}