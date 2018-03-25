package com.tory.dmzj.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Point
import android.graphics.PointF
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager

import java.lang.reflect.Method

//常用单位转换的辅助类
class DensityUtils private constructor() {
    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        /**
         * dp转px
         *
         * @param context
         * @param dpVal
         * @return
         */
        fun dp2px(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dpVal, context.resources.displayMetrics).toInt()
        }

        /**
         * sp转px
         *
         * @param context
         * @param spVal
         * @return
         */
        fun sp2px(context: Context, spVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    spVal, context.resources.displayMetrics).toInt()
        }

        /**
         * px转dp
         *
         * @param context
         * @param pxVal
         * @return
         */
        fun px2dp(context: Context, pxVal: Float): Float {
            val scale = context.resources.displayMetrics.density
            return pxVal / scale
        }

        /**
         * px转sp
         *
         * @param context
         * @param pxVal
         * @return
         */
        fun px2sp(context: Context, pxVal: Float): Float {
            return pxVal / context.resources.displayMetrics.scaledDensity
        }


        fun getScreenW(c: Context): Int {
            return c.resources.displayMetrics.widthPixels
        }

        fun getScreenH(c: Context): Int {
            return c.resources.displayMetrics.heightPixels
        }

        fun getScreenSize(ctx: Context): Point {
            val dm = ctx.resources.displayMetrics
            return Point(dm.widthPixels, dm.heightPixels)
        }

        fun getScreenDpiSize(ctx: Context): PointF {
            val dm = ctx.resources.displayMetrics
            return PointF(dm.widthPixels / dm.density, dm.heightPixels / dm.density)
        }

        /**
         * 获取屏幕真实的宽高
         * @param context
         * @return
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun getScreenRealSize(context: Context): Point {
            val size = Point()
            val winMgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = winMgr.defaultDisplay
            val dm = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= 17) {
                display.getRealMetrics(dm)
                size.x = dm.widthPixels
                size.y = dm.heightPixels
            } else {
                try {
                    val method = Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics::class.java)
                    method.invoke(display, dm)
                    size.x = dm.widthPixels
                    size.y = dm.heightPixels
                } catch (e: Exception) {
                    display.getMetrics(dm)
                    size.x = dm.widthPixels
                    size.y = dm.heightPixels
                }

            }
            return size
        }

        fun getDensity(ctx: Context): String? {
            var densityStr: String? = null
            val density = ctx.resources.displayMetrics.densityDpi
            when (density) {
                DisplayMetrics.DENSITY_LOW -> densityStr = "LDPI"
                DisplayMetrics.DENSITY_MEDIUM -> densityStr = "MDPI"
                DisplayMetrics.DENSITY_TV -> densityStr = "TVDPI"
                DisplayMetrics.DENSITY_HIGH -> densityStr = "HDPI"
                DisplayMetrics.DENSITY_XHIGH -> densityStr = "XHDPI"
                DisplayMetrics.DENSITY_400 -> densityStr = "XMHDPI"
                DisplayMetrics.DENSITY_XXHIGH -> densityStr = "XXHDPI"
                DisplayMetrics.DENSITY_XXXHIGH -> densityStr = "XXXHDPI"
            }
            return densityStr
        }
    }
}
