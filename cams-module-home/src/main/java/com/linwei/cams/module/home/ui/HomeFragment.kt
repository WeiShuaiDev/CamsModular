package com.linwei.cams.module.home.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseApplication
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.module.home.databinding.HomeFragmentHomeBinding
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.tool.XToolReporter
import com.linwei.tool.ui.crash.CrashReporterActivity
import com.linwei.tool.ui.network.NetworkReporterActivity

@Route(path = HomeRouterTable.PATH_FRAGMENT_HOME)
class HomeFragment : CommonBaseFragment<HomeFragmentHomeBinding>() {

    @Autowired
    lateinit var title: String

    override fun hasInjectARouter(): Boolean = true

    override fun initEvent() {
        mViewBinding.homeStartBtn.setOnClickListener {
            XToolReporter.disableAndzu()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(mContext)) {
                //If the draw over permission is not available open the settings screen
                //to grant the permission.
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:com.linwei.camsmodular")
                )
                startActivityForResult(intent, 1)
            } else {
                XToolReporter.initBubbles(CommonBaseApplication.application)
            }
        }

        mViewBinding.homeCrashBtn.setOnClickListener {
            startActivity(Intent(mContext, CrashReporterActivity::class.java))
        }

        mViewBinding.homeNetworkBtn.setOnClickListener {
            startActivity(Intent(mContext, NetworkReporterActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                XToolReporter.initBubbles(CommonBaseApplication.application)
            } else { //Permission is not available
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            XToolReporter.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}