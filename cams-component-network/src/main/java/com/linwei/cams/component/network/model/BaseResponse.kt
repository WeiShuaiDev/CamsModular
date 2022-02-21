package com.linwei.cams.component.network.model

data  class BaseResponse<T>(val errorCode: Int, val errorMsg: String, val data: T)