package com.tory.dmzj.networks.apis

import com.tory.dmzj.bean.UserModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * Created by tory on 2017/12/3.
 */
interface UserService {


    @FormUrlEncoded
    @POST("login/m_confirm")
    fun login(@Field("nickname") nickname: String, @Field("passwd") passwd: String): Observable<UserModel>
}