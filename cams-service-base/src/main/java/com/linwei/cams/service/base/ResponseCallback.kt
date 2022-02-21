package com.linwei.cams.service.base

interface ResponseCallback<T> {
    /**
     * 成功回调
     * @param t:T 返回数据
     */
    fun onSuccess(t: T?)

    /**
     * 失败回调
     * @param errorMessage:ErrorMessage
     */
    fun onFailed(errorMessage: ErrorMessage)

}