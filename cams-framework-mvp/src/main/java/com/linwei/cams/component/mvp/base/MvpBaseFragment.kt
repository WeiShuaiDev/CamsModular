package com.linwei.cams.component.mvp.base

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.component.common.ktx.snackBar
import com.linwei.cams.component.common.utils.toast
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构 基类MvpBaseFragment
 * -----------------------------------------------------------------------
 */
abstract class MvpBaseFragment<T : ViewBinding, P : IMvpPresenter> : CommonBaseFragment<T>(),
    IMvpView {

    protected var mMvpPresenter: P? = null

    protected abstract fun getPresenter(): P

    override fun onViewCreatedExpand(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedExpand(view, savedInstanceState)
        mMvpPresenter=getPresenter()
    }

    override fun showLoadingDialog(message: String) {

    }

    override fun showSnackBar(message: String) {
        activity?.window?.decorView?.snackBar(message)
    }

    override fun showToast(message: String?) {
        toast(message)
    }

    override fun dismissLoadingDialog() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpPresenter?.onDestroy()
    }
}