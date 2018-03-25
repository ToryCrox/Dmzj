package com.tory.dmzj.ui

import android.os.Bundle
import com.tory.dmzj.R
import com.tory.dmzj.bean.SubscribeModel
import com.tory.dmzj.helper.SpHelper
import com.tory.dmzj.networks.RetrofitHelper
import com.tory.dmzj.ui.base.BaseSliderActivity
import com.tory.dmzj.utils.L
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by tory on 2018/1/14.
 */
class MineSubscribeActivity : BaseSliderActivity(){
    /**
     * http://v2.api.dmzj.com/UCenter/subscribe
     */
    /**
     * GET
     * 参数
     * type=0&letter=all&sub_type=1&page=0&uid=18487332
     * type
     *
     *
     *
     */

    /**
     *
    [
    {
    "name": "干物妹小埋",
    "sub_update": "第06卷",
    "sub_img": "http://images.dmzj.com/webpic/19/ganmeimei.jpg",
    "sub_uptime": 1507094840,
    "sub_first_letter": "g",
    "sub_readed": 0,
    "id": 11267,
    "status": "连载中"
    },
    {
    "name": "妖精的尾巴",
    "sub_update": "推图",
    "sub_img": "http://images.dmzj.com/webpic/1/yjdwb.jpg",
    "sub_uptime": 1504705430,
    "sub_first_letter": "y",
    "sub_readed": 0,
    "id": 1081,
    "status": "已完结"
    }
     *
     */

    override fun bindLayout(): Int = R.layout.activity_mine_subscribe

    override fun doBusiness(ss: Bundle?) {
        var uid =  SpHelper.getUser()?.uid
        if(uid == null) return

        RetrofitHelper.comicService.getCartoonSubscribe(0, "all", "1",
                0, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<SubscribeModel>>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        L.e(TAG, "fetchData error", e)
                    }

                    override fun onNext(models: List<SubscribeModel>) {
                        L.d("onNext infos=" + models)

                    }
                })
    }

}