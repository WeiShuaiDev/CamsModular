package com.linwei.cams.service.home.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.service.base.ResponseCallback
import com.linwei.cams.service.home.model.HomeBean

interface HomeProvider : IProvider {

    fun fetchHomeData(page: Int, callback: ResponseCallback<HomeBean>)


}