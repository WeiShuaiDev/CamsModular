package com.linwei.cams.framework.mvi.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.component.common.ktx.snackBar
import com.linwei.cams.component.common.utils.toast
import com.linwei.cams.framework.mvi.mvi.ViewModelDelegate
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import com.linwei.cams.framework.mvi.mvi.view.MviView
import com.quyunshuo.androidbaseframemvvm.base.utils.network.AutoRegisterNetListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkStateChangeListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkTypeEnum

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:  `MVI` 架构 `Fragment` 核心基类
 *-----------------------------------------------------------------------
 */
abstract class MviBaseFragment<VM : MviViewModel, VB : ViewBinding> : CommonBaseFragment<VB>(),
    ViewModelDelegate<VM>, MviView<VM>, NetworkStateChangeListener {

    protected var mViewModel: VM? = null

    protected var mAutoRegisterNet: AutoRegisterNetListener? = null

    override fun onViewCreatedExpand(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedExpand(view, savedInstanceState)
        initViewModel()
        initNetworkListener()
    }

    /**
     * 初始化ViewModel
     */
    protected fun initViewModel() {
        mViewModel = createViewModel()
        if (mViewModel == null) {
            mViewModel = obtainViewModel(fetchVMClass())
        }

        if (mViewModel != null) {
            lifecycle.addObserver(mViewModel!!)
        }
        bindViewModel(mViewModel, this)
    }

    /**
     * 初始化网络状态监听
     * @return Unit
     */
    private fun initNetworkListener() {
        if (mAutoRegisterNet == null) {
            mAutoRegisterNet = AutoRegisterNetListener(this)
        }
        lifecycle.addObserver(mAutoRegisterNet!!)
    }

    /**
     * 根据 `ViewModel` 的 [vmClass],获取 `ViewModel` 对象
     * @param vmClass [Class]
     * @return  [ViewModel]
     */
    private fun obtainViewModel(vmClass: Class<VM>): VM = obtainViewModel(viewModelStore, vmClass)

    /**
     * 根据 `ViewModel` 的 [vmClass],获取 `ViewModel` 对象
     * @param store [ViewModelStore]
     * @param vmClass [Class]
     * @return  [ViewModel]
     */
    private fun obtainViewModel(store: ViewModelStore, vmClass: Class<VM>): VM =
        createViewModelProvider(store).get(vmClass)

    /**
     * 创建 [ViewModelProvider] 对象
     * @param store [ViewModelStore]
     * @return [ViewModelProvider] 对象
     */
    private fun createViewModelProvider(store: ViewModelStore): ViewModelProvider =
        ViewModelProvider(store, mViewModelFactory)

    /**
     * 获取 [ViewModelProvider.Factory] 对象
     * @return mViewModelFactory [ViewModelProvider.Factory]
     */
    protected fun getViewModelFactory(): ViewModelProvider.Factory = mViewModelFactory

    /**
     * 创建 `ViewModel` 类
     * @return [VM]
     */
    override fun createViewModel(): VM? = null

    override fun showSnackBar(message: String) {
        activity?.window?.decorView?.snackBar(message)
    }

    override fun showLoadingDialog(message: String) {
    }

    override fun dismissLoadingDialog() {
    }

    override fun showToast(message: String?) {
        toast(message)
    }

    override fun networkConnectChange(isConnected: Boolean) {
        if (!isConnected) {
            toast("网络出现问题~~")
        }
    }

    override fun networkTypeChange(type: NetworkTypeEnum) {
    }

    /**
     *  获取 `ViewModel` 对象
     *  @return mViewModel [VM]
     */
    protected fun getViewModel(): VM? = mViewModel

    /**
     * 销毁 'ViewModel'
     */
    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.let {
            lifecycle.removeObserver(mViewModel!!)
            mViewModel = null
        }

        mAutoRegisterNet?.let {
            lifecycle.removeObserver(mAutoRegisterNet!!)
            mAutoRegisterNet = null
        }


    }
}