package com.linwei.cams.component.network.callback

import com.linwei.cams.component.network.ApiConstants
import com.linwei.cams.component.network.model.BaseResponse
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

abstract class RxJavaCallback<T> : Observer<BaseResponse<T>> {
    override fun onComplete() {
    }

    override fun onSubscribe(disposable: Disposable) {
        disposable.isDisposed
    }

    override fun onNext(response: BaseResponse<T>) {
        val data: BaseResponse<T> = response

        if (ApiConstants.REQUEST_SUCCESS == data.errorCode) {
            System.out.println("code=${data.errorCode} data=${data.data} ")
            onSuccess(data.errorCode, data.data)
        } else {
            System.out.println("code=${data.errorCode} data=${data.errorMsg} ")
            onFailure(data.errorCode, data.errorMsg)
        }
    }

    override fun onError(throwable: Throwable) {
        throwable.message?.let {
            System.out.println("onError"+it)
            onFailure(ApiConstants.REQUEST_FAILURE, it)
        }
    }


    /**
     * 接口请求成功，回调 [onSuccess] 方法
     * @param code [String] 成功状态码
     * @param data [T] 响应数据
     */
    open fun onSuccess(code: Int?, data: T?) {}

    /**
     * 接口请求失败，回调 [onFailure] 方法
     * @param code [String] 失败状态码
     * @param message [String] 失败信息
     */
    open fun onFailure(code: Int?, message: String?) {}

}