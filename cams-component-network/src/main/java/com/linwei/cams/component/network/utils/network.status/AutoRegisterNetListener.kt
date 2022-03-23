package com.quyunshuo.androidbaseframemvvm.base.utils.network

import androidx.lifecycle.*

/**
 * 自动注册网络状态监听
 * 使用的是[androidx.lifecycle.LifecycleObserver]来同步生命周期
 */
class AutoRegisterNetListener constructor(listener: NetworkStateChangeListener) :
    DefaultLifecycleObserver {

    /**
     * 当前需要自动注册的监听器
     */
    private var mListener: NetworkStateChangeListener? = null

    init {
        mListener = listener
    }

    override fun onResume(owner: LifecycleOwner) {
        mListener?.run { NetworkStateClient.setListener(this) }
    }

    override fun onPause(owner: LifecycleOwner) {
        NetworkStateClient.removeListener()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        NetworkStateClient.removeListener()
        mListener = null
    }

}