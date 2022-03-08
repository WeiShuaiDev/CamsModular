package com.linwei.cams.component.common.ext

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * 显示Toast
 */
fun Context.toast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    if (message?.isNotEmpty() == true) {
        Toast.makeText(this, message, length).show()
    }
}

/**
 * 显示SnackBar
 */
fun View.snackBar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, length)
        .show()
}

