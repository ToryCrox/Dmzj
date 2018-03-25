package com.tory.dmzj.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.tory.dmzj.R
import com.tory.dmzj.recycler.BaseRecyclerAdapter
import com.tory.dmzj.recycler.EndlessRecyclerOnScrollListener


abstract class BaseRecyclerPageFragment<T : BaseRecyclerAdapter<*>>: BasePageFragment() {

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView :RecyclerView
    @BindView(R.id.swipe_container)
    lateinit var mSwipeRefresh: SwipeRefreshLayout
    protected var mPageIndex = 1
    protected lateinit var mRecyclerAdapter : T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRecyclerAdapter = createAdapter()
    }

    abstract fun  createAdapter() : T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView.adapter = mRecyclerAdapter
        mRecyclerView.layoutManager = createLayoutManager(activity!!)
        createItemDecoration(activity!!)?.let { mRecyclerView.addItemDecoration(it) }
        mSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(activity!!, R.color.colorPrimary))
        mSwipeRefresh.setOnRefreshListener {
            mPageIndex = 1
            prepareFetchData(  true)
        }

        mRecyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener(mRecyclerView.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(currentPage: Int) {
                mPageIndex = currentPage
                prepareFetchData(true)
            }
        })
    }

    open fun createLayoutManager(context : Context) : RecyclerView.LayoutManager{
        return LinearLayoutManager(context)
    }

    open fun createItemDecoration(context: Context) : RecyclerView.ItemDecoration?{
        return DividerItemDecoration(context, LinearLayout.VERTICAL)
    }

}// Required empty public constructor