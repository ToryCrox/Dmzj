package com.tory.dmzj.utils

import android.content.res.Resources
import android.os.Build
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View

/**
 * Created by tao.xu2 on 2016/8/19.
 */
object Utilities {

    val ATLEAST_MARSHMALLOW = Build.VERSION.SDK_INT >= 23

    val ATLEAST_LOLLIPOP_MR1 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1

    val ATLEAST_LOLLIPOP = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    val ATLEAST_KITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

    val ATLEAST_JB_MR1 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1

    val ATLEAST_JB_MR2 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2

    fun isRtl(res: Resources): Boolean {
        return ATLEAST_JB_MR1 && res.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
    }

    fun initSwipeRefresh(swipeRefreshLayout: SwipeRefreshLayout?) {
        if (swipeRefreshLayout == null) return
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }

}
