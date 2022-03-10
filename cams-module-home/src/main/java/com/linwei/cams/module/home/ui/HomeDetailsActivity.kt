package com.linwei.cams.module.home.ui

import com.linwei.cams.component.common.ext.getExtra
import com.linwei.cams.component.common.ext.setResult
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.linwei.cams.module.home.databinding.HomeActivityHomeDetailsBinding

class HomeDetailsActivity :
    MvpBaseActivity<HomeActivityHomeDetailsBinding, MvpPresenter<IMvpView, IMvpModel>>() {

    override fun initView() {
        val name = intent.getExtra("name", "没有名称") as String
        mViewBinding.tvHomeTitle.text = name
    }

    override fun initData() {
    }

    override fun initEvent() {
        mViewBinding.btnStart.setOnClickListener {
            setResult("title", "HomeDetailsActivity")
            finish()
        }
    }

    override fun getPresenter(): MvpPresenter<IMvpView, IMvpModel>? {
        return null
    }
}