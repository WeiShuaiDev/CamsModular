package com.linwei.cams.module.home.ui

import android.Manifest
import com.linwei.cams.component.common.ktx.getExtra
import com.linwei.cams.component.common.ktx.requestPermission
import com.linwei.cams.component.common.ktx.setResult
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.linwei.cams.module.home.databinding.HomeActivityHomeDetailsBinding

class HomeDetailsActivity :
    MvpBaseActivity<HomeActivityHomeDetailsBinding, MvpPresenter<IMvpView, IMvpModel>>() {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
        val name = intent.getExtra("name", "没有名称") as String
        mViewBinding.homeTitleTv.text = name
    }

    override fun initData() {
       requestPermission(
            success = {
                showToast("请求成功！")
            }, failed = {
                showToast("请求失败！")
            }).launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun initEvent() {
        mViewBinding.homeStartBtn.setOnClickListener {
            setResult("title", "HomeDetailsActivity")
            finish()
        }
    }

    override fun getPresenter(): MvpPresenter<IMvpView, IMvpModel>? {
        return null
    }
}