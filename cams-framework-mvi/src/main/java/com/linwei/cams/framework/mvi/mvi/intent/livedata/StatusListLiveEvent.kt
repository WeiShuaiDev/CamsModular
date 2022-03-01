package com.linwei.cams.framework.mvi.mvi.intent.livedata

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/28
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:  MVVM架构状态事件总线，提供 `observe`、`observeForever` 两种方式。
 *          这里定义四种状态值: `StatusCode.LOADING`:加载中；`StatusCode.SUCCESS`: 成功状态；
 *          `StatusCode.FAILURE`:失败状态; `StatusCode.ERROR`: 错误状态
 *-----------------------------------------------------------------------
 */
class StatusListLiveEvent : LiveDataListEvent<MviViewEvent>() {

    fun observe(owner: LifecycleOwner, observer: StatusLiveObserver) {
        super.observe(owner) {
            if (it != null)
                observer.onStatusChanges(it)
        }
    }

    fun observeForever(observer: StatusLiveObserver) {
        super.observeForever {
            if (it != null)
                observer.onStatusChanges(it)
        }
    }

    interface StatusLiveObserver {
        fun onStatusChanges(event:List<MviViewEvent>)
    }

}