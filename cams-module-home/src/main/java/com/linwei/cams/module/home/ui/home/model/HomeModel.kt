package com.linwei.cams.module.home.ui.home.model

import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.module.home.ui.home.contract.IHomeModel
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.HomeBean
import com.linwei.cams.service.home.provider.HomeProviderHelper

class HomeModel : MvpModel(), IHomeModel {

    private val mHomeProvider by lazy {
        HomeProviderHelper.getHomeProvider()
    }

    override fun fetchHomeData(
        page: Int,
        callback: ResponseCallback<HomeBean>
    ) {
        mHomeProvider.fetchHomeData(page, callback)
    }

}