package com.linwei.cams.module.home.ui.home.model

import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.module.home.http.ApiServiceWrap
import com.linwei.cams.module.home.ui.home.contract.IHomeModel
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean
import com.linwei.cams.service.home.provider.HomeProviderHelper

class HomeModel : MvpModel(), IHomeModel {

    private val mHomeProvider= HomeProviderHelper.getHomeProvider()

    override fun fetchHomeData(
        page: Int,
        callback: ResponseCallback<HomeBean>
    ) {
        mHomeProvider?.fetchHomeData(page, callback)
    }

    override fun fetchBannerData(callback: ResponseCallback<List<BannerBean>>) {
        mHomeProvider?.fetchBannerData(callback)
    }

}