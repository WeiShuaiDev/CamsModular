package com.linwei.cams.module.project.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.module.project.databinding.ProjectActivityProjectBinding
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.project.ProjectRouterTable

@Route(path = ProjectRouterTable.PATH_ACTIVITY_PROJECT)
class ProjectActivity : CommonBaseActivity<ProjectActivityProjectBinding>() {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}