package com.linwei.cams.module.project.ui.project.mvi.model

import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.cams.service.project.model.ProjectTreeDetailsBean

data class MviViewState(
    val fetchStatus: FetchStatus = FetchStatus.NotFetched,

    val projectTreeList: List<ProjectTreeBean> = emptyList(),

    val projectTreeDetailsList: List<ProjectTreeDetailsBean> =emptyList()
)

