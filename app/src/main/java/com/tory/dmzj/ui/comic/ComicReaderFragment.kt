package com.tory.dmzj.ui.comic

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tory.dmzj.R
import com.tory.dmzj.ui.base.BasePageFragment

import rx.Subscription


/**
 * @Author: tory
 * Create: 2017/9/5
 * Update: ${UPDATE}
 */
class ComicReaderFragment : BasePageFragment() {


    //protected ComicReaderAdapter mRecyclerAdapter;

    internal var mSubscription: Subscription? = null


    override fun bindLayout(): Int = R.layout.fragment_comic_reader

    override fun onDestroy() {
        super.onDestroy()
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) {
            mSubscription!!.unsubscribe()
        }
    }

    override fun fetchData() {

    }

    companion object {

        val TAG = "ComicReaderFragment"

        fun newInstance(): ComicReaderFragment {
            return ComicReaderFragment()
        }
    }


}
