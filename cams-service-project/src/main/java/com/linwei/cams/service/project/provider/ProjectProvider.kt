package com.linwei.cams.service.project.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.cams.service.project.model.ProjectTreeDetailsBean

interface ProjectProvider : IProvider {

    suspend fun fetchProjectTreeData(): PageState<List<ProjectTreeBean>>

    suspend fun fetchProjectTreeDetailsData():ProjectTreeDetailsBean



}