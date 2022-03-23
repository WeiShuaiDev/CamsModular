package com.linwei.cams.module.project.ui.project.mvi.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.observeState
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.framework.mvi.mvi.view.MviView
import com.linwei.cams.module.project.ui.project.mvi.intent.ProjectViewModel
import com.linwei.cams.module.project.ui.project.mvi.model.MviViewState
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.cams.service.project.model.ProjectTreeDetailsBean

interface ProjectView : MviView<ProjectViewModel> {

    override fun bindViewModel(viewModel: ProjectViewModel?, owner: LifecycleOwner) {
        super.bindViewModel(viewModel, owner)
        viewModel?.let {
            it.viewState.run {
                observeState(owner, MviViewState::projectTreeList) {
                    projectTreeDataToView(it)
                }
                observeState(owner, MviViewState::projectTreeDetailsList) {
                    projectTreeDetailsDataToView(it)
                }
                observeState(owner, MviViewState::fetchStatus) {
                    when (it) {
                        is FetchStatus.Fetched -> {

                        }
                        is FetchStatus.NotFetched -> {

                        }
                        is FetchStatus.Fetching -> {

                        }
                    }
                }
            }
        }
    }

    override fun bindOtherMviViewEvent(event: MviViewEvent) {
        super.bindOtherMviViewEvent(event)
        //扩展Event监听

    }


    /**
     * 项目树数据更新到View
     * @param data [ProjectTreeBean]
     */
    fun projectTreeDataToView(data: List<ProjectTreeBean>?)

    /**
     * 项目树详情数据更新到View
     * @param data [ProjectTreeDetailsBean]
     */
    fun projectTreeDetailsDataToView(data: List<ProjectTreeDetailsBean>?)
}