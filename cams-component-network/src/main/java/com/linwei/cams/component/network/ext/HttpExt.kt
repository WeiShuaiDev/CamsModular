package com.linwei.cams.component.network.ext

import com.linwei.cams.component.network.callback.RxJavaCallback
import com.linwei.cams.component.network.model.BaseResponse
import com.linwei.cams.component.network.utils.CoroutineScopeHelper
import com.linwei.cams.component.network.utils.LaunchBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Observable<BaseResponse<T>>.execute(
    subscriber: RxJavaCallback<T>
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)
}

fun <T> Flow<T>.commonCatch(action: suspend FlowCollector<T>.(cause: Throwable) -> Unit): Flow<T> {
    return this.catch {
        if (it is UnknownHostException || it is SocketTimeoutException) {
            //"发生网络错误，请稍后重试"
        } else {
            //"请求失败，请重试"
        }
        action(it)
    }
}

fun <T> CoroutineScope.rxLaunch(init: LaunchBuilder<T>.() -> Unit) {
    val scopeHelper = CoroutineScopeHelper<T>(this)
    scopeHelper.rxLaunch(init)
}
