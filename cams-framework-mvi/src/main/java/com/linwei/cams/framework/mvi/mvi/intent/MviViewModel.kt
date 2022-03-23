package com.linwei.cams.framework.mvi.mvi.intent

import androidx.lifecycle.*
import com.linwei.cams.framework.mvi.ktx.asLiveData
import com.linwei.cams.framework.mvi.ktx.setEvent
import com.linwei.cams.framework.mvi.ktx.setPostEvent
import com.linwei.cams.framework.mvi.mvi.intent.livedata.StatusListLiveEvent
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVI架构  `ViewModel` 模块
 *-----------------------------------------------------------------------
 */
open class MviViewModel : ViewModel(), DefaultLifecycleObserver {

    private val _viewEvents: StatusListLiveEvent = StatusListLiveEvent()
    val viewEvent = _viewEvents.asLiveData()

    /**
     * 同步更新状态
     * @param event [MviViewEvent] Event
     */
    fun sendUpdateEvents(vararg event: MviViewEvent) {
        _viewEvents.setEvent(event.toList())
    }

    /**
     * 异步更新状态
     * @param event [MviViewEvent] Event
     */
    fun postUpdateEvents(vararg evnet: MviViewEvent) {
        _viewEvents.setPostEvent(evnet.toList())
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        _viewEvents.call()
    }
}