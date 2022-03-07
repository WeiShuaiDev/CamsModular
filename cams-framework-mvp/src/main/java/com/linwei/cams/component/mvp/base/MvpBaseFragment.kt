package com.linwei.cams.component.mvp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.trello.rxlifecycle4.android.FragmentEvent
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构 基类MvpBaseFragment
 * -----------------------------------------------------------------------
 */
abstract class MvpBaseFragment<T : ViewBinding, P : IMvpPresenter> : CommonBaseFragment<T>(), IMvpView {

    private lateinit var mMvpPresenter: P

    private var mLifecycleSubject: BehaviorSubject<FragmentEvent> = BehaviorSubject.create()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mLifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLifecycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        mLifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        mLifecycleSubject.onNext(FragmentEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        mLifecycleSubject.onNext(FragmentEvent.STOP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
    }

    override fun onDetach() {
        super.onDetach()
        mLifecycleSubject.onNext(FragmentEvent.DETACH)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleSubject.onNext(FragmentEvent.DESTROY)
    }
}