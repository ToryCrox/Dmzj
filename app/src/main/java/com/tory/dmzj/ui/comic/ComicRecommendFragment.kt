package com.tory.dmzj.ui.comic

import butterknife.BindView
import com.tory.dmzj.R
import com.tory.dmzj.networks.RetrofitHelper
import com.tory.dmzj.ui.base.BasePageFragment
import com.tory.dmzj.utils.L
import com.tory.dmzj.widget.banner.BannerEntity
import com.tory.dmzj.widget.banner.BannerView
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @Author: tory
 * Create: 2017/9/2
 */
class ComicRecommendFragment : BasePageFragment() {

    @BindView(R.id.bannerView)
    lateinit var bannerView: BannerView

    override fun bindLayout(): Int = R.layout.fragment_comic_recommend

    override fun fetchData() {

        RetrofitHelper.comicService.getCartoonRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe({ cartoonRecommendModels ->
                    L.d("onNext cartoonRecommendModels=" + cartoonRecommendModels)
                    var cartoonRecommendModel = cartoonRecommendModels.first()
                    var data = cartoonRecommendModel.data
                    var bannerEntices = mutableListOf<BannerEntity>()
                    data?.forEach {
                        var bannerEntity = BannerEntity(it.url, it.title, it.cover)
                        bannerEntices.add(bannerEntity)
                    }
                    bannerView.build(bannerEntices)
                }, { e -> L.e(TAG, "fetchData error"+e)})

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
