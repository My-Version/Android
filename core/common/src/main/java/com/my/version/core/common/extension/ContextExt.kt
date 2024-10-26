package com.my.version.core.common.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, this.getString(message), Toast.LENGTH_SHORT).show()
}