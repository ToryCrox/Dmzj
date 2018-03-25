package com.tory.dmzj.widget.banner

import android.app.Activity
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tory.dmzj.R
import com.tory.dmzj.utils.DensityUtils
import kotlinx.android.synthetic.main.layout_custom_banner.view.*

import java.util.ArrayList
import java.util.concurrent.TimeUnit

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class BannerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr), BannerAdapter.ViewPagerOnItemClickListener {
    internal var viewPager: ViewPager
    internal var points: LinearLayout
    private var compositeSubscription: CompositeSubscription? = null
    //默认轮播时间，10s
    private var delayTime = 5
    private val imageViewList: MutableList<ImageView>
    private var bannerList = mutableListOf<BannerEntity>()
    //选中显示Indicator
    private var selectRes = R.drawable.shape_dots_select
    //非选中显示Indicator
    private var unSelectRes = R.drawable.shape_dots_default
    //当前页的下标
    private var currentPos: Int = 0

    private var isStopScroll = false


    init {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_banner, this, true)
        imageViewList = ArrayList()
        viewPager = layout_banner_viewpager
        points = layout_banner_points_group
    }


    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    fun delayTime(time: Int): BannerView {
        this.delayTime = time
        return this
    }


    /**
     * 设置Points资源 Res
     *
     * @param selectRes 选中状态
     * @param unselcetRes 非选中状态
     */
    fun setPointsRes(selectRes: Int, unselcetRes: Int) {
        this.selectRes = selectRes
        this.unSelectRes = unselcetRes
    }


    /**
     * 图片轮播需要传入参数
     */
    fun build(list: List<BannerEntity>) {
        destroy()
        if (list.size == 0) {
            this.visibility = View.GONE
            return
        }
        bannerList.addAll(list)
        val pointSize: Int = bannerList.size
        if (pointSize == 2) {
            bannerList.addAll(list)
        }
        //判断是否清空 指示器点
        if (points.childCount != 0) {
            points.removeAllViewsInLayout()
        }
        //初始化与个数相同的指示器点
        for (i in 0 until pointSize) {
            val dot = View(context)
            dot.setBackgroundResource(unSelectRes)
            val params = LinearLayout.LayoutParams(
                    DensityUtils.dp2px(context, 5F),
                    DensityUtils.dp2px(context, 5F))
            params.leftMargin = 10
            dot.layoutParams = params
            dot.isEnabled = false
            points.addView(dot)
        }
        points.getChildAt(0).setBackgroundResource(selectRes)
        for (i in bannerList.indices) {
            val imageView = ImageView(context)
            Glide.with(context)
                    .load(bannerList[i].img)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(imageView)
            imageViewList.add(imageView)
        }
        //监听图片轮播，改变指示器状态
        viewPager.clearOnPageChangeListeners()
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(pos: Int) {
                var pos = pos
                pos = pos % pointSize
                currentPos = pos
                for (i in 0 until points.childCount) {
                    points.getChildAt(i).setBackgroundResource(unSelectRes)
                }
                points.getChildAt(pos).setBackgroundResource(selectRes)
                title.text = bannerList[pos].title
            }

            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_IDLE -> if (isStopScroll) {
                        startScroll()
                    }
                    ViewPager.SCROLL_STATE_DRAGGING -> {
                        stopScroll()
                        compositeSubscription!!.unsubscribe()
                    }
                }
            }
        })
        val bannerAdapter = BannerAdapter(imageViewList)
        viewPager.adapter = bannerAdapter
        bannerAdapter.notifyDataSetChanged()
        bannerAdapter.setmViewPagerOnItemClickListener(this)
        //图片开始轮播
        startScroll()
    }


    /**
     * 图片开始轮播
     */
    private fun startScroll() {
        compositeSubscription = CompositeSubscription()
        isStopScroll = false
        val subscription = Observable.timer(delayTime.toLong(), TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isStopScroll) {
                        return@subscribe
                    }
                    isStopScroll = true
                    viewPager.currentItem = viewPager.currentItem + 1
                }
        compositeSubscription!!.add(subscription)
    }


    /**
     * 图片停止轮播
     */
    private fun stopScroll() {
        isStopScroll = true
    }


    fun destroy() {
        if (compositeSubscription != null) {
            compositeSubscription!!.unsubscribe()
        }
    }

    /**
     * 设置ViewPager的Item点击回调事件
     */
    override fun onItemClick() {

    }
}