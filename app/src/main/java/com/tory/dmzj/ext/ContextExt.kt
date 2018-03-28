package com.tory.dmzj.ext

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.widget.Toast

/**
 * Created by tory on 2018/1/14.
 */

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

@ColorInt
fun Context.getCompatColor(@ColorRes id : Int) = ContextCompat.getColor(this, id)

