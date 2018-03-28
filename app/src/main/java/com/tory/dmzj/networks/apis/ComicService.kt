package com.tory.dmzj.networks.apis


import com.tory.dmzj.bean.CartoonCategoryModel
import com.tory.dmzj.bean.CartoonRecommendModel
import com.tory.dmzj.bean.LatestComicInfo
import com.tory.dmzj.bean.SubscribeModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Author: tory
 * Create: 2017/9/2
 *
 * baseUrl: http://v2.api.dmzj.com/
 */
interface ComicService {

    @GET("latest/{type}/{page}.json")
    fun getLatestComics(@Path("type") type: Int, @Path("page") page: Int): Observable<List<LatestComicInfo>>

    @GET("recommend.json")
    fun getCartoonRecommend(): Observable<List<CartoonRecommendModel>>


    @GET("0/category.json")
    fun getCartoonCategory() : Observable<List<CartoonCategoryModel>>

    @GET("UCenter/subscribe")
    fun getCartoonSubscribe(@Query("type") type : Int,
                            @Query("letter") letter : String,
                            @Query("sub_type") subType : String,
                            @Query("page") page : Int,
                            @Query("uid") uid : String) : Observable<List<SubscribeModel>>
}
