package com.linwei.cams.module.square.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.module.square.databinding.SquareActivitySquareBinding
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.square.SquareRouterTable

@Route(path = SquareRouterTable.PATH_ACTIVITY_SQUARE)
class SquareActivity : CommonBaseActivity<SquareActivitySquareBinding>() {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}