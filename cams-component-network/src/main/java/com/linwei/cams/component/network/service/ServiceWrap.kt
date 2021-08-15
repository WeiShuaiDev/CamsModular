package com.linwei.cams.component.network.service

interface ServiceWrap<T> {

    fun getIdentify():String

    fun getModuleName():String

    fun getHost():String

    fun fetchRealService():Class<T>

}