package com.linwei.cams.component.network.model

data  class ResponseData<T>(val errorCode: Int, val errorMsg: String, val data: T?)