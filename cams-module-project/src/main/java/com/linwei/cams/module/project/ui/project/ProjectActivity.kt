package com.linwei.cams.module.project.ui.project

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.framework.mvi.base.MviBaseActivity
import com.linwei.cams.module.project.databinding.ProjectActivityProjectBinding
import com.linwei.cams.module.project.ui.project.mvi.intent.ProjectViewModel
import com.linwei.cams.module.project.ui.project.mvi.view.ProjectView
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.cams.service.project.model.ProjectTreeDetailsBean

@Route(path = ProjectRouterTable.PATH_ACTIVITY_PROJECT)
class ProjectActivity : MviBaseActivity<ProjectActivityProjectBinding, ProjectViewModel>(),
    ProjectView {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {

    }

    override fun initData() {
        //获取数据
        mViewModel?.fetchProjectTreeData()

        mViewModel?.fetchProjectTreeDetailsData()
    }

    override fun initEvent() {
    }

    override fun projectTreeDataToView(data: List<ProjectTreeBean>?) {
        mViewBinding.projectContentTv.text = data.toString()
    }

    override fun projectTreeDetailsDataToView(data: List<ProjectTreeDetailsBean>?) {

    }
}