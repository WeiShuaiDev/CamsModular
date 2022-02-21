package com.linwei.cams.module.home.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.callback.RxJavaCallback
import com.linwei.cams.component.network.ext.execute
import com.linwei.cams.module.home.http.ApiServiceWrap
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.ResponseCallback
import com.linwei.cams.service.home.provider.HomeProvider
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.HomeBean

@Route(path = HomeRouterTable.PATH_SERVICE_HOME)
class HomeProviderImpl : HomeProvider {
    override fun init(context: Context?) {

    }

    override fun fetchHomeData(
        page: Int,
        callback: ResponseCallback<HomeBean>
    ) {
         ApiClient.getInstance().getService(ApiServiceWrap()).getArticleListData(1)
            .execute(object : RxJavaCallback<HomeBean>() {

                override fun onSuccess(code: Int?, data: HomeBean?) {
                    super.onSuccess(code, data)
                    callback.onSuccess(data)
                }

                override fun onFailure(code: Int?, message: String?) {
                    super.onFailure(code, message)
                    callback.onFailed(ErrorMessage(code,message))
                }
            })
    }
}