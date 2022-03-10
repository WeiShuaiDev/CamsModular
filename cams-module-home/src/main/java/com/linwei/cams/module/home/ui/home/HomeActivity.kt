package com.linwei.cams.module.home.ui.home

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.ext.getExtra
import com.linwei.cams.component.common.ext.launch
import com.linwei.cams.component.common.ext.startActivityForResult
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.module.home.databinding.HomeActivityHomeBinding
import com.linwei.cams.module.home.ui.HomeDetailsActivity
import com.linwei.cams.module.home.ui.home.contract.IHomeView
import com.linwei.cams.module.home.ui.home.presenter.HomePresenter
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean

@Route(path = HomeRouterTable.PATH_ACTIVITY_HOME)
class HomeActivity : MvpBaseActivity<HomeActivityHomeBinding, HomePresenter>(), IHomeView {

    @Autowired
    lateinit var title: String

    override fun hasInjectARouter(): Boolean = true

    override fun getPresenter(): HomePresenter = HomePresenter(this)

    override fun initEvent() {

    }

    override fun initData() {
        mMvpPresenter?.requestHomeData(1)
    }

    override fun initView() {
       val activityResultLauncher= startActivityForResult{
            val title= it.data?.getExtra("title", "没有标题") as String
            mViewBinding.tvHomeTitle.text=title
        }
        mViewBinding.btnStart.setOnClickListener {
            activityResultLauncher.launch(mContext,HomeDetailsActivity::class.java,"name","HomeActivity")
        }
    }

    override fun updateHomeDataToView(homeBean: HomeBean) {
        mViewBinding.tvHomeContent.text = homeBean.toString()
    }

    override fun updateBannerDataToView(bannerList: List<BannerBean>) {
        mViewBinding.tvHomeContent.text = bannerList.toString()

    }

}