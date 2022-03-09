package com.linwei.cams.service.home.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean

interface HomeProvider : IProvider {

    fun fetchHomeData(page: Int, callback: ResponseCallback<HomeBean>)

    fun fetchBannerData(callback: ResponseCallback<List<BannerBean>>)

    fun routerHomeFragment():Fragment

}