package com.linwei.cams.module.home.di

import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.module.home.http.ApiService
import com.linwei.cams.module.home.http.ApiServiceWrap
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiHomeModule {

    /**
     * Home模块的[ApiService]依赖提供方法
     *
     * @param apiServiceWrap ApiServiceWrap
     * @return ApiService
     */
    @Provides
    @Singleton
    fun provideProjectApiService(apiServiceWrap: ApiServiceWrap): ApiService {
        return ApiClient.getInstance().getService(apiServiceWrap)
    }
}