package com.linwei.cams.component.mvp.base

import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.snackBar
import com.linwei.cams.component.common.utils.toast
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.quyunshuo.androidbaseframemvvm.base.utils.network.AutoRegisterNetListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkStateChangeListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkStateClient
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkTypeEnum

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构 基类MvpBaseActivity
 * -----------------------------------------------------------------------
 */
abstract class MvpBaseActivity<T : ViewBinding, P : IMvpPresenter> :
    CommonBaseActivity<T>(), IMvpView, NetworkStateChangeListener {

    protected var mMvpPresenter: P? = null

    protected abstract fun getPresenter(): P?

    override fun onCreateExpand() {
        super.onCreateExpand()
        mMvpPresenter=getPresenter()

    }


    override fun showLoadingDialog(message: String) {

    }

    override fun showSnackBar(message: String)=window.decorView.snackBar(message)

    override fun showToast(message: String?) {
        toast(message)
    }

    override fun networkConnectChange(isConnected: Boolean) {
        if(!isConnected){
            toast("网络出现问题~~")
        }
    }

    override fun networkTypeChange(type: NetworkTypeEnum) {
    }

    override fun dismissLoadingDialog() {

    }

    override fun onResume() {
        super.onResume()
        NetworkStateClient.setListener(this)
    }

    override fun onPause() {
        super.onPause()
        NetworkStateClient.removeListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpPresenter?.onDestroy()
        NetworkStateClient.removeListener()
    }
}