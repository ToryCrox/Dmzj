package com.tory.dmzj.ui.comic

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tory.dmzj.R
import com.tory.dmzj.bean.LatestComicInfo
import com.tory.dmzj.networks.RetrofitHelper
import com.tory.dmzj.recycler.BaseRecyclerAdapter
import com.tory.dmzj.recycler.BaseViewHolder
import com.tory.dmzj.ui.base.BaseRecyclerPageFragment
import com.tory.dmzj.utils.L
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


class ComicUpdateFragment : BaseRecyclerPageFragment<ComicUpdateFragment.LatestComicAdapter>() {


    override fun bindLayout(): Int = R.layout.fragment_commic_update

    override fun createAdapter(): LatestComicAdapter {
        return LatestComicAdapter()
    }

    override fun fetchData() {
        val subscribe: Any = RetrofitHelper.comicService.getLatestComics(100, mPageIndex - 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe({latestComicInfos ->
                    L.d("onNext infos=" + latestComicInfos)
                    refreshData(latestComicInfos)
                }, { e -> L.e(TAG, "fetchData error", e)},{
                    mSwipeRefresh.isRefreshing = false
                })

    }

    private fun refreshData(latestComicInfos: List<LatestComicInfo>) {
        if (mPageIndex == 1) {
            mRecyclerAdapter.clear()
        }
        mRecyclerAdapter.addAll(latestComicInfos)
        mRecyclerAdapter.notifyDataSetChanged()
    }

    inner class LatestComicAdapter : BaseRecyclerAdapter<LatestComicInfo>(R.layout.item_lastet_comic_info) {

        var dateFormate = SimpleDateFormat("yyyy-MM-dd")
        override fun convert(holder: BaseViewHolder, item: LatestComicInfo) {
            holder.setText(R.id.tv_title, item.title)
                    .setText(R.id.tv_authors, item.authors)
                    .setText(R.id.tv_types, item.types)
                    .setText(R.id.tv_updatetime, dateFormate.format(Date(item.lastUpdatetime*1000L)))
            Glide.with(this@ComicUpdateFragment).load(item.cover)
                    .into(holder.getView<View>(R.id.iv_cover) as ImageView)
        }
    }

    companion object {
        val TAG = "ComicUpdateFragment"

        // TODO: Rename and change types and number of parameters
        fun newInstance(): ComicUpdateFragment {
            return ComicUpdateFragment()
        }
    }

}// Required empty public constructor


