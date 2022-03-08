package com.linwei.cams.component.mvp.base

import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ext.snackBar
import com.linwei.cams.component.common.ext.toast
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView

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
    CommonBaseActivity<T>(), IMvpView {

    protected var mMvpPresenter: P? = null

    protected abstract fun getPresenter(): P

    override fun onCreateExpand() {
        super.onCreateExpand()
        mMvpPresenter=getPresenter()
    }

    override fun showLoadingDialog(message: String) {

    }

    override fun showSnackBar(message: String)=window.decorView.snackBar(message)

    override fun showToast(message: String?) {
        mContext.toast(message)
    }

    override fun dismissLoadingDialog() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpPresenter?.onDestroy()
    }
}