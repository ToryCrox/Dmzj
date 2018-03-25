package com.tory.dmzj.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout

/**
 * Created by tao.xu2 on 2016/8/19.
 * 参考文章:https://laobie.github.io/android/2016/02/15/status-bar-demo.html
 * 代码:https://github.com/laobie/StatusBarUtil/blob/master/library/src/main/java/com/jaeger/library/StatusBarUtil.java
 */
object StatusBarHelper {
    private val TAG = "StatusBarHelper"


    val DEFAULT_STATUS_BAR_ALPHA = 255

    /**
     * 设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    @JvmOverloads
    fun setColor(activity: Activity, color: Int, statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = calculateStatusColor(color, statusBarAlpha)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val decorView = activity.window.decorView as ViewGroup
            val count = decorView.childCount
            if (count > 0 && decorView.getChildAt(count - 1) is StatusBarView) {
                decorView.getChildAt(count - 1).setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
            } else {
                val statusView = createStatusBarView(activity, color, statusBarAlpha)
                decorView.addView(statusView)
            }
            setRootView(activity)
        }
    }

    /**
     * 设置状态栏纯色 不加半透明效果
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    fun setColorNoTranslucent(activity: Activity, color: Int) {
        setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA)
    }


    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private fun createStatusBarView(activity: Activity, color: Int, alpha: Int): StatusBarView {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = StatusBarView(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                SystemConfigUtils.getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
        return statusBarView
    }

    /**
     * 生成一个和状态栏大小相同的彩色矩形条
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private fun createStatusBarView(activity: Activity, color: Int): StatusBarView {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = StatusBarView(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                SystemConfigUtils.getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(color)
        return statusBarView
    }

    /**
     * 设置根布局参数
     */
    private fun setRootView(activity: Activity) {
        val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
        val rootView = contentView.getChildAt(0) as ViewGroup
        if (rootView == null) {
            contentView.fitsSystemWindows = true
            contentView.clipToPadding = true
        } else {
            rootView.fitsSystemWindows = true
            rootView.clipToPadding = true
        }

    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity       需要设置的activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏颜色值
     */
    fun setColorForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout, color: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        // 生成一个状态栏大小的矩形
        // 添加 statusBarView 到布局中
        val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
        if (contentLayout.childCount > 0 && contentLayout.getChildAt(0) is StatusBarView) {
            contentLayout.getChildAt(0).setBackgroundColor(color)
        } else {
            val statusBarView = createStatusBarView(activity, color)
            contentLayout.addView(statusBarView, 0)
        }
        // 内容布局不是 LinearLayout 时,设置padding top
        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1).setPadding(0, SystemConfigUtils.getStatusBarHeight(activity), 0, 0)
        }
        // 设置属性
        val drawer = drawerLayout.getChildAt(1) as ViewGroup
        drawerLayout.fitsSystemWindows = false
        contentLayout.fitsSystemWindows = false
        contentLayout.clipToPadding = true
        drawer.fitsSystemWindows = false
    }


    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private fun calculateStatusColor(color: Int, alpha: Int): Int {
        val a = alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }

    class StatusBarView : View {
        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

        constructor(context: Context) : super(context) {}
    }
}
/**
 * 设置状态栏颜色
 *
 * @param activity 需要设置的 activity
 * @param color    状态栏颜色值
 */
