package com.linwei.cams.module.mine.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.module.mine.databinding.MineActivityMineBinding
import com.linwei.cams.service.mine.MineRouterTable

@Route(path = MineRouterTable.PATH_ACTIVITY_MINE)
class MineActivity : CommonBaseActivity<MineActivityMineBinding>() {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}