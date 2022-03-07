package com.linwei.cams.component.mvp.base

import android.app.Activity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.trello.rxlifecycle4.android.ActivityEvent
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构 基类MvpBaseActivity
 * -----------------------------------------------------------------------
 */
abstract class MvpBaseActivity<T : ViewBinding, P :IMvpPresenter> :
    CommonBaseActivity<T>(),
    IMvpView {

    private lateinit var mMvpPresenter: P

    private var mLifecycleSubject: BehaviorSubject<ActivityEvent> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLifecycleSubject.onNext(ActivityEvent.CREATE)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    override fun onStart() {
        super.onStart()
        mLifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onPause() {
        super.onPause()
        mLifecycleSubject.onNext(ActivityEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        mLifecycleSubject.onNext(ActivityEvent.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleSubject.onNext(ActivityEvent.DESTROY)
    }
}