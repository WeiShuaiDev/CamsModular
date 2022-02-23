package com.linwei.cams.component.mvvm.base

import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.component.mvvm.mvvm.MvvmView

/**
 * 基类MvvmBaseFragment
 */
abstract class MvvmBaseFragment<T : ViewBinding> : CommonBaseFragment<T>(), MvvmView {
}