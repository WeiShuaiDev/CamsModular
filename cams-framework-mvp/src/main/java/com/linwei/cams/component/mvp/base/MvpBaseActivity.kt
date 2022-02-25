package com.linwei.cams.component.mvp.base

import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.mvp.mvp.view.MvpView

/**
 * 基类MvpBaseActivity
 */
abstract class MvpBaseActivity<T: ViewBinding> : CommonBaseActivity<T>(), MvpView {
}