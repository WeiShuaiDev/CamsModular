package com.linwei.cams.framework.mvi.mvi.intent.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVI架构  `SingleLiveListEvent` 是[MutableLiveData] 负责处理多维度一次性Event
 * 比如我们在请求开始时发出ShowLoading，网络请求成功后发出DismissLoading与Toast事件
 * 如果我们在请求开始后回到桌面，成功后再回到App,这样有一个事件就会被覆盖，因此将所有事件通过List存储
 *-----------------------------------------------------------------------
 */
open class LiveDataListEvent<T> : MutableLiveData<List<T>>() {
    private val mPending: AtomicBoolean = AtomicBoolean(false)
    private val  mEventList = mutableListOf<List<T>>()


    /**
     * 设置此LiveData数据当前activity或者Fragment的观察者，会给此activity或者Fragment在前台时回调数据。
     * @param owner [LifecycleOwner]
     * @param observer [Observer]
     */
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in List<T>>) {
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                mEventList.clear()
                observer.onChanged(t)
            }
        }
    }

    /**
     * 1.设置永远观察者，永远不会被自动删除。您需要手动调用removeObserver（Observer）以停止观察此LiveData，
     * 2.设置后此LiveData，一直处于活动状态，不管是否在前台哪里都会获得回调。
     * @param observer [Observer]
     */
    @MainThread
    override fun observeForever(observer: Observer<in List<T>>) {
        super.observeForever {
            if (mPending.compareAndSet(true, false)) {
                mEventList.clear()
                observer.onChanged(it)
            }
        }
    }

    @MainThread
    override fun setValue(t: List<T>?) {
        mPending.set(true)
        t?.let {
            mEventList.add(it)
        }
        val list = mEventList.flatten()
        super.setValue(list)
    }

    /**
     * 清空数据
     */
    fun call() {
        value = null
    }
}