package com.tory.dmzj.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by tory on 2018/1/6.
 */
class CartoonCategoryModel : BaseItem{

    @SerializedName("tag_id")
    var tagId: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("cover")
    var cover: String? = null
}