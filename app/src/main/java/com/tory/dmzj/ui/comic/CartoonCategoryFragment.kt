package com.tory.dmzj.ui.comic

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide

import com.tory.dmzj.R

import com.tory.dmzj.bean.CartoonCategoryModel
import com.tory.dmzj.networks.RetrofitHelper
import com.tory.dmzj.recycler.BaseRecyclerAdapter
import com.tory.dmzj.recycler.BaseViewHolder
import com.tory.dmzj.ui.base.BaseRecyclerPageFragment
import com.tory.dmzj.utils.L
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class CartoonCategoryFragment : BaseRecyclerPageFragment<CartoonCategoryFragment.CategoryAdapter>() {

    override fun createAdapter(): CategoryAdapter {
        return CategoryAdapter()
    }

    override fun bindLayout(): Int =R.layout.fragment_cartoon_category

    override fun createLayoutManager(context: Context): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 3)
    }

    override fun createItemDecoration(context: Context): RecyclerView.ItemDecoration? {
        return null
    }

    private fun refreshData(cartoonCategoryModels: List<CartoonCategoryModel>) {
        mRecyclerAdapter.clear()
        mRecyclerAdapter.addAll(cartoonCategoryModels)
        mRecyclerAdapter.notifyDataSetChanged()
    }

    override fun fetchData() {
        RetrofitHelper.comicService.getCartoonCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<CartoonCategoryModel>>() {
                    override fun onCompleted() {
                        mSwipeRefresh.isRefreshing = false
                    }

                    override fun onError(e: Throwable) {
                        L.e(TAG, "fetchData error", e)
                    }

                    override fun onNext(cartoonCategoryModels: List<CartoonCategoryModel>) {
                        L.d("onNext infos=" + cartoonCategoryModels)
                        refreshData(cartoonCategoryModels)
                    }
                })
    }

    inner class CategoryAdapter : BaseRecyclerAdapter<CartoonCategoryModel>(R.layout.item_cartoon_category) {
        override fun convert(holder: BaseViewHolder, item: CartoonCategoryModel) {
            holder.setText(R.id.title, item.title)
            Glide.with(this@CartoonCategoryFragment).load(item.cover).into(holder.getView(R.id.iv_cover))
        }

    }


    companion object {

        val TAG = "CartoonCategoryFragment"

        fun newInstance(): CartoonCategoryFragment{
            return CartoonCategoryFragment()
        }
    }
}// Required empty public constructor
