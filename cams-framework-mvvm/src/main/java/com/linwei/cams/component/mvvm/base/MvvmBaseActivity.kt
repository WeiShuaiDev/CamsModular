package com.linwei.cams.component.mvvm.base

import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.mvvm.mvvm.MvvmView

/**
 * 基类MvvmBaseActivity
 */
abstract class MvvmBaseActivity<T : ViewBinding> : CommonBaseActivity<T>(), MvvmView {


}