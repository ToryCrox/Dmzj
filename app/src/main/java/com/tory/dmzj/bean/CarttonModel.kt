package com.tory.dmzj.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by tory on 2017/12/10.
 */

class CarttonModel : BaseItem{

    @SerializedName("cover")
    val cover: String? = null
    @SerializedName("title")
    val title: String? = null
    @SerializedName("sub_title")
    val subTitle: String? = null

    @SerializedName("type")
    val type: Int = 0
    @SerializedName("url")
    val url: String? = null
    @SerializedName("objId")
    val obj_id: Int = 0
    @SerializedName("status")
    val status: String? = null

    override fun toString(): String {
        return "CarttonModel(cover=$cover, title=$title, subTitle=$subTitle, type=$type, url=$url, obj_id=$obj_id, status=$status)"
    }


}
