package com.linwei.cams.component.common.ext

import android.content.Context
import com.linwei.cams.component.common.utils.Preference
import kotlin.properties.ReadWriteProperty

object DelegatesExt {

    fun <T> getPreference(context: Context, name: String, default: T): ReadWriteProperty<Any?, T> =
        Preference(context, name, default)
}

