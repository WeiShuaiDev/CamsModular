package com.linwei.cams.component.network.callback

import com.linwei.cams.component.network.exception.ApiException
import io.reactivex.rxjava3.functions.Consumer

abstract class ErrorConsumer : Consumer<Throwable> {
    @Throws(Exception::class)
    override fun accept(throwable: Throwable) {
        //对异常进行处理
        val exception: ApiException = if (throwable is ApiException) {
            throwable
        } else {
            ApiException.handleException(throwable)
        }
        //调用error方法
        error(exception)
    }

    //使用时实现error方法即可。
    protected abstract fun error(e: ApiException)
}
