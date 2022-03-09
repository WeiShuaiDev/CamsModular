package com.linwei.cams.service.base.callback

import com.linwei.cams.service.base.ErrorMessage

interface ResponseCallback<T> {
    /**
     * 成功回调
     * @param t:T 返回数据
     */
    fun onSuccess(data: T)

    /**
     * 失败回调
     * @param errorMessage:ErrorMessage
     */
    fun onFailed(errorMessage: ErrorMessage)

}