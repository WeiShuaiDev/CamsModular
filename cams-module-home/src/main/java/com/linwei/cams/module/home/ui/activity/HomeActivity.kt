package com.linwei.cams.module.home.ui.activity

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.module.home.databinding.HomeActivityHomeBinding
import com.linwei.cams.module.home.provider.HomeProviderImpl
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.ResponseCallback
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.HomeBean

@Route(path = HomeRouterTable.PATH_ACTIVITY_HOME)
class HomeActivity : CommonBaseActivity<HomeActivityHomeBinding>() {

    @Autowired
    lateinit var title: String

    override fun hasInjectARouter(): Boolean=true

    override fun initEvent() {
    }

    override fun initData() {
        HomeProviderImpl().fetchHomeData(1, object : ResponseCallback<HomeBean> {
            override fun onSuccess(t: HomeBean?) {
                mViewBinding.homeContentTv.text = t.toString()
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                mViewBinding.homeContentTv.text = errorMessage.message
            }
        })
    }

    override fun initView() {
    }
}