package com.linwei.cams.framework.mvi.mvi.intent

import androidx.lifecycle.*
import com.linwei.cams.framework.mvi.ext.asLiveData
import com.linwei.cams.framework.mvi.mvi.intent.livedata.SingleLiveEvent
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.framework.mvi.mvi.model.MviViewState

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

    private val _viewStates: MutableLiveData<MviViewState> = MutableLiveData()
    val viewState = _viewStates.asLiveData()

    private val _viewEvents: SingleLiveEvent<MviViewEvent> = SingleLiveEvent()
    val viewEvent = _viewEvents.asLiveData()


    private fun emit(viewState: MviViewState) {
        _viewStates.value = viewState
    }

    private fun emit(viewEvent: MviViewEvent) {
        _viewEvents.value = viewEvent
    }
}