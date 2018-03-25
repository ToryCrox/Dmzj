package com.tory.dmzj.ui.comic

import com.tory.dmzj.R
import com.tory.dmzj.bean.CartoonRecommendModel
import com.tory.dmzj.networks.RetrofitHelper
import com.tory.dmzj.ui.base.BasePageFragment
import com.tory.dmzj.utils.L
import com.tory.dmzj.widget.banner.BannerEntity
import kotlinx.android.synthetic.main.fragment_comic_recommend.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @Author: tory
 * Create: 2017/9/2
 */
class ComicRecommendFragment : BasePageFragment() {
    override fun bindLayout(): Int = R.layout.fragment_comic_recommend

    override fun fetchData() {
        RetrofitHelper.comicService.getCartoonRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<CartoonRecommendModel>>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        L.e(TAG, "fetchData error", e)
                    }

                    override fun onNext(cartoonRecommendModels: List<CartoonRecommendModel>) {
                        L.d("onNext cartoonRecommendModels=" + cartoonRecommendModels)
                        var cartoonRecommendModel = cartoonRecommendModels.first()
                        var data = cartoonRecommendModel.data
                        var bannerEntices = mutableListOf<BannerEntity>()
                        data?.forEach {
                            var bannerEntity = BannerEntity(it.url, it.title,it.cover)
                            bannerEntices.add(bannerEntity)
                        }
                        bannerView.build(bannerEntices)
                    }
                })
    }

    companion object {

        val TAG = "ComicRecommendFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ComicRecommendFragment.
         */
        fun newInstance(): ComicRecommendFragment {
            return ComicRecommendFragment()
        }
    }


}// Required empty public constructor
