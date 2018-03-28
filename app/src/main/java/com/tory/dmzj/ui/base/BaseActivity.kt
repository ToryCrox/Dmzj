package com.tory.dmzj.ui.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.os.TraceCompat
import android.support.v7.widget.Toolbar
import android.view.View
import com.tory.dmzj.R
import com.tory.dmzj.utils.L
import com.tory.dmzj.utils.StatusBarHelper
import com.tory.dmzj.utils.Utilities
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


abstract class BaseActivity : RxAppCompatActivity() {

    protected val TAG = this.javaClass.simpleName

    var toolbar: Toolbar? = null
        protected set
    protected var mTitle: String?= null

    //  mToolbar.setTitle(title);
    var toolbarTitle: String?
        get() = mTitle
        set(title) {
            if (toolbar != null) {
                this.mTitle = title
                supportActionBar!!.title = mTitle
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutId = bindLayout()
        if (layoutId > 0) {
            setContentView(layoutId)
        }
        setThemeColor()
        initToolbar()
        initView()
        doBusiness(savedInstanceState)
    }

    protected fun setThemeColor() {
        setStatusBar()
    }

    protected fun initToolbar() {
        var toolbarView : View? = findViewById(R.id.toolbar)

        if (toolbarView != null) {
            toolbar = toolbarView as Toolbar
            setSupportActionBar(toolbar)
        }
        setDisplayHomeAsUpEnabled(true)
        setToolbarBackpress()
    }

    //监听toolbar左上角后退按钮
    fun setToolbarBackpress() {
        if (toolbar != null) {
            toolbar!!.setNavigationOnClickListener { onBackPressed() }
        }

    }

    protected fun setToolbarScrolled(scrolled: Boolean) {
        if (toolbar != null && toolbar!!.layoutParams is AppBarLayout.LayoutParams) {
            val lp = toolbar!!.layoutParams as AppBarLayout.LayoutParams
            if (scrolled) {
                lp.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            } else {
                lp.scrollFlags = 0
            }

            toolbar!!.layoutParams = lp
        }
    }

    protected fun setDisplayHomeAsUpEnabled(enable: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enable)
            actionBar.setDisplayShowHomeEnabled(false)
        }
    }

    protected fun setStatusBar() {
        if (Utilities.ATLEAST_LOLLIPOP) return
        val statusbarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        StatusBarHelper.setColor(this, statusbarColor)
    }


    fun showFragment(tag: String, show: Boolean, executeImmediately: Boolean) {
        TraceCompat.beginSection("showFragment - " + tag)
        val fm = supportFragmentManager
        if (fm == null) {
            L.w(TAG, "Fragment manager is null for : " + tag)
            return
        }


        var fragment: Fragment? = fm.findFragmentByTag(tag)
        if (!show && fragment == null) {
            // Nothing to show, so bail early.
            return
        }

        val transaction = fm.beginTransaction()
        if (show) {
            if (fragment == null) {
                L.d(TAG, "showFragment: fragment need create: " + tag)
                fragment = createNewFragmentForTag(tag)
                transaction.add(getFragmentContainer(tag), fragment, tag)
            } else {
                L.d(TAG, "showFragment: fragment is all ready created " + tag)
                transaction.show(fragment)
            }
        } else {
            transaction.hide(fragment)
        }

        transaction.commitAllowingStateLoss()
        if (executeImmediately) {
            fm.executePendingTransactions()
        }
        TraceCompat.endSection()
    }

    private fun showOsFragment(tag: String, show: Boolean, executeImmediately: Boolean) {

    }

    fun getFragmentManagerByTag(tag: String): Any {
        return supportFragmentManager
    }

    open fun getFragmentContainer(tag: String): Int {
        throw IllegalStateException("Unexpected fragmentContainer: " + tag)
    }

    open fun createNewFragmentForTag(tag: String): Fragment {
        throw IllegalStateException("Unexpected fragment: " + tag)
    }

    /**
     * 绑定渲染视图的布局文件
     *
     * @return 布局文件资源id
     */
    @LayoutRes
    abstract fun bindLayout(): Int

    /**
     * 初始化控件
     */
    abstract fun initView()

    /**
     * 业务处理操作（onCreate方法中调用）
     *
     */
    abstract fun doBusiness(savedInstanceState: Bundle?)
}