package com.linwei.cams.module.project.http

import com.linwei.cams.component.network.service.ServiceWrap
import com.linwei.cams.module.home.Service

class ApiServiceWrap : ServiceWrap<ApiService> {

    override fun getIdentify(): String = Service.MODULE_ID

    override fun getModuleName(): String = Service.MODULE_NAME

    override fun getHost(): String = Service.HOST

    override fun fetchRealService(): Class<ApiService> = ApiService::class.java
}