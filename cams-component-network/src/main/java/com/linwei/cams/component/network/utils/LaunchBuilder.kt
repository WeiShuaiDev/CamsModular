package com.linwei.cams.component.network.utils

class LaunchBuilder<T> {
    lateinit var onRequest: (suspend () -> T)
    var onSuccess: ((data: T) -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null
}