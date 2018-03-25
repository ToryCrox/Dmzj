package com.tory.dmzj.bean

import com.google.gson.annotations.SerializedName

/**
 * @Author: tory
 * Create: 2017/9/2
 * Update: ${UPDATE}
 */
class LatestComicInfo : BaseItem{

    /**
     * id : 27458
     * title : 我的氪金女友
     * islong : 2
     * authors : 近江のこ
     * types : 科幻/爱情/后宫
     * cover : http://images.dmzj.com/webpic/7/kegejinnvyou.jpg
     * status : 连载中
     * last_update_chapter_name : 四格番外22
     * last_update_chapter_id : 62824
     * last_updatetime : 1504361522
     */

    @SerializedName("id")
    var id: Int = 0
    @SerializedName("title")
    var title: String? = null
    @SerializedName("islong")
    var islong: Int = 0
    @SerializedName("authors")
    var authors: String? = null
    @SerializedName("types")
    var types: String? = null
    @SerializedName("cover")
    var cover: String? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("last_update_chapter_name")
    var lastUpdateChapterName: String? = null
    @SerializedName("last_update_chapter_id")
    var lastUpdateChapterId: Int = 0
    @SerializedName("last_updatetime")
    var lastUpdatetime: Int = 0

    override fun toString(): String {
        return "LatestComicInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", islong=" + islong +
                ", authors='" + authors + '\'' +
                ", types='" + types + '\'' +
                ", cover='" + cover + '\'' +
                ", status='" + status + '\'' +
                ", lastUpdateChapterName='" + lastUpdateChapterName + '\'' +
                ", lastUpdateChapterId=" + lastUpdateChapterId +
                ", lastUpdatetime=" + lastUpdatetime +
                '}'
    }
}
