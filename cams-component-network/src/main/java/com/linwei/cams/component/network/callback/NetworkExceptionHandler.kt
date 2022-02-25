package com.linwei.cams.component.network.callback

import kotlinx.coroutines.CoroutineExceptionHandler
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class NetworkExceptionHandler(
    private val onException: (e: Throwable) -> Unit = {}
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        onException.invoke(exception)
        if (exception is UnknownHostException || exception is SocketTimeoutException) {
            //"发生网络错误，请稍后重试")
        } else {
            //"请求失败，请重试"
        }
    }
}