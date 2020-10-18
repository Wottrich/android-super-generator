package io.github.wottrich.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import io.github.wottrich.R

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 17/10/2020
 *
 * Copyright © 2020 SuperGenerator. All rights reserved.
 *
 */
 
fun Context.copyText(textToCopy: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    val clipData = ClipData.newPlainText(getString(R.string.good_lucky), textToCopy)
    clipboard.setPrimaryClip(clipData)
    toast(R.string.copied_numbers)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}