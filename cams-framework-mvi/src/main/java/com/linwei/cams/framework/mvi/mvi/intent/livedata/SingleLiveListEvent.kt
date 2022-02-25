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
class SingleLiveListEvent<T> : MutableLiveData<List<T>>() {
    private val mPending = AtomicBoolean(false)
    private val  mEventList = mutableListOf<List<T>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in List<T>>) {
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                mEventList.clear()
                observer.onChanged(t)
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
}