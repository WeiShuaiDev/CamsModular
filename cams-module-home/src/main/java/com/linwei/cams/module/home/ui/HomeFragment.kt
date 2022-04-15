package com.linwei.cams.module.home.ui

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.module.home.databinding.HomeFragmentHomeBinding
import com.linwei.cams.service.home.HomeRouterTable
import dagger.hilt.android.AndroidEntryPoint

@Route(path = HomeRouterTable.PATH_FRAGMENT_HOME)
class HomeFragment : CommonBaseFragment<HomeFragmentHomeBinding>() {

    @Autowired
    lateinit var title: String

    override fun hasInjectARouter(): Boolean = true

    override fun initEvent() {
    }

    override fun initData() {
    }

    override fun initView() {
    }
}