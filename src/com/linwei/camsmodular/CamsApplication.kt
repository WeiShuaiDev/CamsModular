package com.linwei.camsmodular

import com.linwei.cams.component.common.base.CommonBaseApplication
import org.greenrobot.eventbus.EventBus


class CamsApplication : CommonBaseApplication() {

    override fun onCreate() {
        // 开启EventBusAPT,优化反射效率 当组件作为App运行时需要将添加的Index注释掉 因为找不到对应的类了
        EventBus
            .builder()
//            .addIndex(MainEventIndex())
            .installDefaultEventBus()
        super.onCreate()
    }
}