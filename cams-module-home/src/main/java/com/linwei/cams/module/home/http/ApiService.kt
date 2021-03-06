package com.linwei.cams.module.home.http

import com.linwei.cams.component.network.model.ResponseData
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("article/list/{page}/json")
    fun getArticleListData(@Path("page") page: Int?): Observable<ResponseData<HomeBean>>


    @GET("banner/json")
    fun getBannerListData(): Observable<ResponseData<List<BannerBean>>>

}