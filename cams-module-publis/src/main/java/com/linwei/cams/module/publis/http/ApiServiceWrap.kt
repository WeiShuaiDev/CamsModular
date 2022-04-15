package com.linwei.cams.module.publis.http

import com.linwei.cams.component.network.service.ServiceWrap
import com.linwei.cams.module.publis.constants.Service
import javax.inject.Inject

class ApiServiceWrap @Inject constructor(): ServiceWrap<ApiService> {

    override fun getIdentify(): String = Service.MODULE_ID

    override fun getModuleName(): String = Service.MODULE_NAME

    override fun getHost(): String = Service.HOST

    override fun fetchRealService(): Class<ApiService> = ApiService::class.java
}