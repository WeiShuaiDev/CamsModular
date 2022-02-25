package com.linwei.cams.component.mvvm.base

import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.mvvm.mvvm.view.MvvmView

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:  `MVVM` 架构 `Activity` 核心基类
 *-----------------------------------------------------------------------
 */
abstract class MvvmBaseActivity<VB : ViewBinding> : CommonBaseActivity<VB>(), MvvmView {


}