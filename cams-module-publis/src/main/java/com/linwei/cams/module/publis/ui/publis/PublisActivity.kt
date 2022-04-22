package com.linwei.cams.module.publis.ui.publis

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.module.publis.R
import com.linwei.cams.module.publis.ui.publis.view.PublisView
import com.linwei.cams.module.publis.ui.publis.viewmodel.PublisViewModel
import com.linwei.cams.service.publis.PublisRouterTable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = PublisRouterTable.PATH_ACTIVITY_PUBLIS)
class PublisActivity : MvvmBaseActivity<PublisViewModel>(),PublisView {

    override fun hasInjectARouter(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.publis_activity_publis

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}