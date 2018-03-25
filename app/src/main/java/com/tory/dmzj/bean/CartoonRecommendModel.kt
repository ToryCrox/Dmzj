package com.tory.dmzj.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by tory on 2017/12/10.
 */
class CartoonRecommendModel : BaseItem{

    @SerializedName("category_id")
    val categoryId: Int = 0
    @SerializedName("title")
    val title: String? = null
    @SerializedName("sort")
    val sort: Int = 0
    @SerializedName("data")
    val data: List<CarttonModel>? = null

    override fun toString(): String {
        return "CartoonRecommendModel(categoryId=$categoryId, title=$title, sort=$sort, data=$data)"
    }
}