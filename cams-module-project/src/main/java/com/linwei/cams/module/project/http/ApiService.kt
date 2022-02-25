package com.linwei.cams.module.project.http

import com.linwei.cams.component.network.model.BaseResponse
import com.linwei.cams.service.project.model.ProjectTreeBean
import retrofit2.http.GET


interface ApiService {

    @GET("project/tree/json")
    suspend fun getProjectTreeData(): BaseResponse<List<ProjectTreeBean>>

}