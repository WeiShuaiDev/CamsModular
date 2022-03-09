package com.linwei.cams.component.network.callback

import com.linwei.cams.component.network.ApiConstants
import com.linwei.cams.component.network.exception.ApiException
import com.linwei.cams.component.network.model.ResponseData
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

abstract class RxJavaCallback<T> : Observer<ResponseData<T>> {
    override fun onComplete() {
    }

    override fun onSubscribe(disposable: Disposable) {
        disposable.isDisposed
    }

    override fun onNext(response: ResponseData<T>) {
        val data: ResponseData<T> = response

        if (ApiConstants.REQUEST_SUCCESS == data.errorCode) {
            if (null != data.data) {
                onSuccess(data.errorCode, data.data)
            } else {
                onFailure(ApiConstants.EMPTY_DATA_ERROR, "")
            }
        } else {
            onFailure(data.errorCode, data.errorMsg)
        }
    }

    override fun onError(throwable: Throwable) {
        val exception = ApiException.handleException(throwable)
        onFailure(exception.code, exception.displayMessage)
    }


    /**
     * 接口请求成功，回调 [onSuccess] 方法
     * @param code [String] 成功状态码
     * @param data [T] 响应数据
     */
    open fun onSuccess(code: Int?, data: T) {}

    /**
     * 接口请求失败，回调 [onFailure] 方法
     * @param code [String] 失败状态码
     * @param message [String] 失败信息
     */
    open fun onFailure(code: Int?, message: String?) {}

}