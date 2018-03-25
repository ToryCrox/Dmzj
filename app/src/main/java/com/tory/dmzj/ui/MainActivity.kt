package com.tory.dmzj.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.SparseArray
import android.view.MenuItem
import com.tory.dmzj.R
import com.tory.dmzj.ui.base.BaseActivity
import com.tory.dmzj.ui.comic.ComicMainFragment
import com.tory.dmzj.ui.comic.MineCenterFragment
import com.tory.dmzj.utils.L
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    val KEY_SHOW_TAG = "key_show_tag"
    private var mShowingFragmentTag: String? = null
    private var mTagMenuIds: SparseArray<String> = SparseArray()


    override fun onSaveInstanceState(outState: Bundle) {
        L.d(TAG, "onSaveInstanceState mShowingFragmentTag: " + mShowingFragmentTag)
        outState.putString(KEY_SHOW_TAG, mShowingFragmentTag)
        super.onSaveInstanceState(outState)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_main //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        setDisplayHomeAsUpEnabled(false)
        navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun doBusiness(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mShowingFragmentTag = savedInstanceState.getString(KEY_SHOW_TAG)
            L.d(TAG, "onCreate mShowingFragmentTag:" + mShowingFragmentTag)
        }
        initTagMenuIds()
        initDefaultFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_comic -> {
                showFragmentById(R.id.navigation_comic)
                return true
            }
            R.id.navigation_news -> {

                return true
            }
            R.id.navigation_me -> {
                showFragmentById(R.id.navigation_me)
                return true
            }
        }
        return false
    }

    private fun initTagMenuIds() {
        mTagMenuIds.clear()
        mTagMenuIds.put(R.id.navigation_comic, ComicMainFragment.FRAGMENT_TAG)
        mTagMenuIds.put(R.id.navigation_me, MineCenterFragment.FRAGMENT_TAG)
        //mTagMenuIds.put(R.id.navigation_dashboard,PartitionListFragment.FRAGMENT_TAG);
        //mTagMenuIds.put(R.id.nav_setting,SettingsFragment.FRAGMENT_TAG);
        //mTagMenuIds.put(R.id.nav_gallery, SsListFragment.FRAGMENT_TAG);
    }

    private fun initDefaultFragment() {
        if (mShowingFragmentTag == null) {
            mShowingFragmentTag = ComicMainFragment.FRAGMENT_TAG
        }

        val size = mTagMenuIds.size()
        for (i in 0 until size) {
            val menuId = mTagMenuIds.keyAt(i)
            val tag = mTagMenuIds.valueAt(i)
            if (mShowingFragmentTag == tag) {
                showFragment(tag, true, true)
                navigation.selectedItemId = menuId
            } else {
                showFragment(tag, false, true)
            }
        }
    }

    fun showFragmentById(id: Int) {
        val tag = mTagMenuIds.get(id)
        showFragmentAndHideOther(tag)
    }

    fun showFragmentAndHideOther(tag: String) {
        hideShowingFragment(tag)
        showFragment(tag, true, false)
        mShowingFragmentTag = tag
    }

    private fun hideShowingFragment(tag: String) {
        if (mShowingFragmentTag != null && !TextUtils.equals(tag, mShowingFragmentTag)) {
            L.d(TAG, "hideShowingFragment: " + mShowingFragmentTag)
            showFragment(mShowingFragmentTag!!, false, false)
        }
    }

    override fun getFragmentContainer(tag: String): Int {
        return R.id.frame_content
    }

    override fun createNewFragmentForTag(tag: String): Fragment {
        if (ComicMainFragment.FRAGMENT_TAG == tag) {
            return ComicMainFragment.newInstance()
        }else if(MineCenterFragment.FRAGMENT_TAG == tag){
            return MineCenterFragment()
        }
        throw IllegalStateException("Unexpected fragment: " + tag)
    }

}
