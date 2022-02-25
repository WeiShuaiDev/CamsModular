package com.linwei.cams.module.project.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.ApiConstants
import com.linwei.cams.module.project.http.ApiServiceWrap
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.cams.service.project.provider.ProjectProvider
import java.lang.Exception

@Route(path = ProjectRouterTable.PATH_SERVICE_PROJECT)
class ProjectProviderImpl : ProjectProvider {
    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }

    override suspend fun fetchProjectTreeData(): PageState<List<ProjectTreeBean>> {

        val projectTreeData = try {
            ApiClient.getInstance().getService(ApiServiceWrap()).getProjectTreeData()
        } catch (e: Exception) {
            return PageState.Error(e)
        }

        projectTreeData.takeIf { it.errorCode == ApiConstants.REQUEST_SUCCESS }?.apply {
            this.data?.let {
                return PageState.Success(it)
            } ?: run {
                return PageState.Error(errorMsg)
            }
        }
        return PageState.Error(projectTreeData.errorMsg)
    }
}