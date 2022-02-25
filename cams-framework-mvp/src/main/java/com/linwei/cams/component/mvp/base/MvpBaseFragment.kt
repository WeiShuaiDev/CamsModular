package com.linwei.cams.component.mvp.base

import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.component.mvp.mvp.view.MvpView

/**
 * 基类MvpBaseFragment
 */
abstract class MvpBaseFragment<T: ViewBinding> : CommonBaseFragment<T>(), MvpView {
}