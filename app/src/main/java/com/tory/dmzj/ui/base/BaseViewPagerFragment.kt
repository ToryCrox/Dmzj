package com.tory.dmzj.ui.base

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import butterknife.BindView

import com.tory.dmzj.R

import kotlinx.android.synthetic.main.fragment_base_view_paper.*
import java.security.cert.Extension


/**
 * @Author: Tory
 * Create: 2016/9/25
 * Update: 2016/9/25
 */
abstract class BaseViewPagerFragment : BaseFragment() {

    protected var mTabItems = mutableListOf<TabItem>()

    @BindView(R.id.viewpager)
    lateinit var mViewPager : ViewPager
    @BindView(R.id.tabLayout)
    lateinit var mTabLayout : TabLayout

    override fun bindLayout(): Int = R.layout.fragment_base_view_paper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    protected open fun initView(view: View?, savedInstanceState: Bundle?) {
        buildTabItems(mTabItems)
        val tabPageAdapter = createPageAdapter()
        mViewPager.adapter = tabPageAdapter
        mTabLayout.setupWithViewPager(viewpager)
    }

    protected open fun createPageAdapter(): BaseViewPager = BaseViewPager(childFragmentManager)

    protected fun addTabItem(title : String, clazz: Class<*>){
        mTabItems.add(TabItem(title, clazz))
    }

    protected abstract fun buildTabItems(tabItems: MutableList<TabItem>)

    protected fun createFragment(clazz : Class<*>) : Fragment? = null

    class TabItem(var title: String, var clazz: Class<*>)

    protected open inner class BaseViewPager(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            //return getItemFromTag(mTabItems[position].clazz)
            var f = getItemFromClass(mTabItems[position].clazz)
            return if(f != null){
                f
            }else{
                mTabItems[position].clazz.newInstance() as Fragment
            }
        }

        protected fun getItemFromClass(clazz: Class<*>): Fragment?  = createFragment(clazz)

        override fun getCount(): Int {
            return mTabItems.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTabItems[position].title
        }
    }
}// Required empty public constructor
