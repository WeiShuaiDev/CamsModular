package com.linwei.cams.component.network.utils

import com.linwei.cams.component.network.callback.NetworkExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CoroutineScopeHelper<T>(private val coroutineScope: CoroutineScope) {
    fun rxLaunch(init: LaunchBuilder<T>.() -> Unit): Job {
        val result = LaunchBuilder<T>().apply(init)
        val handler = NetworkExceptionHandler {
            result.onError?.invoke(it)
        }
        return coroutineScope.launch(handler) {
            val res: T = result.onRequest()
            result.onSuccess?.invoke(res)
        }
    }
}