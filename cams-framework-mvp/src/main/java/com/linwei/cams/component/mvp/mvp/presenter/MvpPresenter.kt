package com.linwei.cams.component.mvp.mvp.presenter

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/4
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构 `Presenter` 接口实现
 * -----------------------------------------------------------------------
 */
abstract class MvpPresenter<V : IMvpView,M : IMvpModel>(
    private var rootView: V?,
    private var model: M?
) : IMvpPresenter, DefaultLifecycleObserver {

    init {
        rootView?.let { v ->
            if (v is LifecycleOwner) {
                v.lifecycle.addObserver(this)
                model?.let { m ->
                    if (m is DefaultLifecycleObserver) {
                        v.lifecycle.addObserver(m)
                    }
                }
            }
        }
    }


    override fun onStart() {

    }

    override fun onDestroy() {
        model?.onDestroy()
        model = null
        rootView = null
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}