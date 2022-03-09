package com.linwei.cams.component.network.transformer


import com.linwei.cams.component.network.ApiConstants
import com.linwei.cams.component.network.exception.ApiException
import com.linwei.cams.component.network.model.ResponseData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jetbrains.annotations.NotNull


class ResponseTransformer<T> : ObservableTransformer<ResponseData<T>, T> {
    @NotNull
    override fun apply(@NotNull upstream: Observable<ResponseData<T>>): ObservableSource<T> {
        return upstream.onErrorResumeNext { throwable ->
            Observable.error(
                ApiException.handleException(
                    throwable
                )
            )
        }.flatMap { responseData -> //请求成功，开始处理你的逻辑吧
            if (ApiConstants.REQUEST_SUCCESS == responseData.errorCode) {
                if (null != responseData.data) {
                    Observable.just(responseData.data)
                } else {
                    Observable.error(ApiException(ApiConstants.EMPTY_DATA_ERROR, ""))
                }
            } else Observable.error(
                ApiException(
                    responseData.errorCode,
                    responseData.errorMsg
                )
            )
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        fun <R> obtain(): ResponseTransformer<R> {
            return ResponseTransformer()
        }
    }
}

