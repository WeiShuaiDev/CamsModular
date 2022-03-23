package com.linwei.cams.module.project.di

import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.module.project.http.ApiService
import com.linwei.cams.module.project.http.ApiServiceWrap
import com.linwei.cams.module.project.provider.ProjectProviderImpl
import com.linwei.cams.service.project.provider.ProjectProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiProjectModule {

    /**
     * Project模块的[ApiService]依赖提供方法
     *
     * @param apiServiceWrap ApiServiceWrap
     * @return ApiService
     */
    @Provides
    @Singleton
    fun provideProjectApiService(apiServiceWrap: ApiServiceWrap): ApiService {
        return ApiClient.getInstance().getService(apiServiceWrap)
    }

//    @Module
//    @InstallIn(SingletonComponent::class)
//    interface Bindings {
//        /**
//         * Project模块的[ProjectProvider]依赖提供方法
//         *
//         * @return ProjectProvider
//         */
//        @Singleton
//        @Binds
//        fun provideProjectProvider(projectProvider: ProjectProviderImpl): ProjectProvider
//    }
}