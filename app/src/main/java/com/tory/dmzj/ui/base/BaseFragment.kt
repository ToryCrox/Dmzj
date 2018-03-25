package com.tory.dmzj.ui.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.tory.dmzj.utils.L

/**
 * @Author: Tory
 * Create: 2016/9/11
 * Update: 2016/9/11
 */
open abstract class BaseFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(KEY_FRAGMENT_HIDDEN)) {
                fragmentManager!!.beginTransaction().hide(this).commit()
            }
        }

        L.d("KEY_TAG "+ javaClass.simpleName)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(KEY_FRAGMENT_HIDDEN, isHidden)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(bindLayout(), container, false)
    }

    @CallSuper
    open override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ButterKnife.bind(this, view)
    }

    @LayoutRes
    abstract fun bindLayout(): Int

    companion object {
        private val KEY_FRAGMENT_HIDDEN = "key_fragment_hidden"
    }
}
