package com.linwei.cams_mvvm.livedatabus

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/28
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构单通道事件总线，提供 `observe`、`observeForever` 两种方式。
 *-----------------------------------------------------------------------
 */
open class LiveDataEvent<T> : MutableLiveData<T>() {

    private val mPending: AtomicBoolean = AtomicBoolean(false)

    /**
     * 设置此LiveData数据当前activity或者Fragment的观察者，会给此activity或者Fragment在前台时回调数据。
     * @param owner [LifecycleOwner]
     * @param observer [Observer]
     */
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    /**
     * 1.设置永远观察者，永远不会被自动删除。您需要手动调用removeObserver（Observer）以停止观察此LiveData，
     * 2.设置后此LiveData，一直处于活动状态，不管是否在前台哪里都会获得回调。
     * @param observer [Observer]
     */
    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        super.observeForever {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        }
    }

    @MainThread
    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    /**
     * 清空数据
     */
    fun call() {
        value = null
    }
}