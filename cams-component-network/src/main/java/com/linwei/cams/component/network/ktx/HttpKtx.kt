package com.linwei.cams.component.network.ktx

import com.linwei.cams.component.network.callback.RxJavaCallback
import com.linwei.cams.component.network.model.ResponseData
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

fun <T> Observable<ResponseData<T>>.execute(
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

//@OptIn(ExperimentalCoroutinesApi::class)
//fun <Result> CoroutineScope.launchSafety(
//    context: CoroutineContext = EmptyCoroutineContext,
//    start: CoroutineStart = CoroutineStart.DEFAULT,
//    block: suspend CoroutineScope.() -> Result, // 这里返回 Result 泛型是为了回掉方便使用
//): StatefulCoroutine<Response> {
//    val newContext = newCoroutineContext(context)
//    val coroutine = SafetyCoroutine<Result>(newContext)
//    coroutine.start(start, coroutine, block)
//    return coroutine
//}


