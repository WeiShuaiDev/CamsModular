package com.linwei.cams.module.main

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.addFragment
import com.linwei.cams.module.main.databinding.MainActivityMainBinding
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.provider.HomeProviderHelper
import com.linwei.cams.service.mine.MineRouterTable
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.publis.PublisRouterTable
import com.linwei.cams.service.square.SquareRouterTable

class MainActivity : CommonBaseActivity<MainActivityMainBinding>() {

    override fun initView() {
      HomeProviderHelper.getHomeProvider()?.routerHomeFragment()?.let {
          supportFragmentManager.addFragment(it, R.id.main_container_fl)
      }
    }

    override fun initData() {
    }

    override fun initEvent() {
        mViewBinding.mainHomeTv.setOnClickListener {
            ARouter.getInstance().build(HomeRouterTable.PATH_ACTIVITY_HOME)
                .withString("title", "首页")
                .navigation()

        }
        mViewBinding.mainProjectTv.setOnClickListener {
            ARouter.getInstance().build(ProjectRouterTable.PATH_ACTIVITY_PROJECT)
                .withString("title", "项目")
                .navigation()
        }
        mViewBinding.mainSquareTv.setOnClickListener {
            ARouter.getInstance().build(SquareRouterTable.PATH_ACTIVITY_SQUARE)
                .withString("title", "广场")
                .navigation()
        }
        mViewBinding.mainPublisTv.setOnClickListener {
            ARouter.getInstance().build(PublisRouterTable.PATH_ACTIVITY_PUBLIS)
                .withString("title", "公众号")
                .navigation()
        }
        mViewBinding.mainMineTv.setOnClickListener {
            ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_MINE)
                .withString("title", "我的")
                .navigation()
        }
    }

}