package com.tory.dmzj.widget.banner

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView

class BannerAdapter internal constructor(private val mList: List<ImageView>) : PagerAdapter() {
    private var pos: Int = 0
    private var mViewPagerOnItemClickListener: ViewPagerOnItemClickListener? = null

    internal fun setmViewPagerOnItemClickListener(mViewPagerOnItemClickListener: ViewPagerOnItemClickListener) {
        this.mViewPagerOnItemClickListener = mViewPagerOnItemClickListener
    }

    override fun getCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    override fun instantiateItem(container: ViewGroup, p: Int): Any {
        var position = p
        //对ViewPager页号求模取出View列表中要显示的项
        position %= mList.size
        if (position < 0) {
            position += mList.size
        }
        val v = mList[position]
        pos = position
        v.scaleType = ImageView.ScaleType.CENTER
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        val vp = v.parent
        if (vp != null) {
            val parent = vp as ViewGroup
            parent.removeView(v)
        }
        v.setOnClickListener {
            if (mViewPagerOnItemClickListener != null) {
                mViewPagerOnItemClickListener!!.onItemClick()
            }
        }
        container.addView(v)
        return v
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {}


    internal interface ViewPagerOnItemClickListener {
        fun onItemClick()
    }
}