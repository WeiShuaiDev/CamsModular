package com.linwei.cams.component.network.ext

import com.linwei.cams.component.network.callback.RxJavaCallback
import com.linwei.cams.component.network.model.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


fun <T> Observable<BaseResponse<T>>.execute(
    subscriber: RxJavaCallback<T>
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)
}