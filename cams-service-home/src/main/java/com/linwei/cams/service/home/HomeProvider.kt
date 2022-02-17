package com.linwei.cams.service.home

import com.alibaba.android.arouter.facade.template.IProvider

interface HomeProvider : IProvider {
    fun fetchHomeData():HomeBean
}