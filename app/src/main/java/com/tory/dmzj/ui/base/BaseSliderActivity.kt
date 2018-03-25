package com.tory.dmzj.ui.base

import android.support.annotation.CallSuper
import android.support.v4.content.ContextCompat
import com.r0adkll.slidr.Slidr
import com.tory.dmzj.R
import com.tory.dmzj.ext.getCompatColor


/**
 * Created by tory on 2018/1/14.
 */
abstract class BaseSliderActivity : BaseActivity() {

    @CallSuper
    open override fun initView() {
        val primary = getCompatColor(R.color.colorPrimary)
        val secondary = getCompatColor(R.color.colorPrimaryDark)
        Slidr.attach(this, primary, secondary)
    }
}