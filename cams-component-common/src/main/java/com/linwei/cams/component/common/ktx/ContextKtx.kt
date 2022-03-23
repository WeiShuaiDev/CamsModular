package com.linwei.cams.component.common.ktx

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar



/**
 * 显示SnackBar
 */
fun View.snackBar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, length)
        .show()
}

