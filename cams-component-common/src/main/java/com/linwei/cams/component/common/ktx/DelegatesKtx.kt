package com.linwei.cams.component.common.ktx

import android.content.Context
import com.linwei.cams.component.common.utils.SharePreferenceUtils
import kotlin.properties.ReadWriteProperty

object DelegatesKtx {

    fun <T> getPreference(context: Context, name: String, default: T): ReadWriteProperty<Any?, T> =
        SharePreferenceUtils(context, name, default)
}

