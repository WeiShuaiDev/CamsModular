package com.linwei.cams.module.publis.ui.publis
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.module.publis.ui.publis.viewmodel.PublisViewModel
import com.linwei.cams.service.publis.PublisRouterTable

@Route(path = PublisRouterTable.PATH_ACTIVITY_PUBLIS)
class PublisActivity : MvvmBaseActivity<PublisViewModel>() {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}