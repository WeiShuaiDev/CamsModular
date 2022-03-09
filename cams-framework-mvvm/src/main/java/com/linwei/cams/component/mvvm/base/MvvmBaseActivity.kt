package com.linwei.cams.component.mvvm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ext.snackBar
import com.linwei.cams.component.common.ext.toast
import com.linwei.cams.component.mvvm.mvvm.ViewModelDelegate
import com.linwei.cams.component.mvvm.mvvm.view.MvvmView
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:  `MVVM` 架构 `Activity` 核心基类
 *-----------------------------------------------------------------------
 */
abstract class MvvmBaseActivity<VM : MvvmViewModel> : CommonBaseActivity<ViewBinding>(),
    ViewModelDelegate<VM>, MvvmView<VM> {

    protected var mViewModel: VM? = null

    override fun onCreateExpand() {
        super.onCreateExpand()
        initViewModel()
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
        createViewModelProvider(store)[vmClass]

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

    override fun createViewModel(): VM? = null

    override fun showSnackBar(message: String) = window.decorView.snackBar(message)

    override fun showLoadingDialog(message: String) {
    }

    override fun dismissLoadingDialog() {
    }

    override fun showToast(message: String?){
        mContext.toast(message)
    }

    /**
     *  获取 `ViewModel` 对象
     *  @return mViewModel [VM]
     */
    protected fun getViewModel(): VM? = mViewModel

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.let {
            lifecycle.removeObserver(mViewModel!!)
            mViewModel = null
        }
    }

}