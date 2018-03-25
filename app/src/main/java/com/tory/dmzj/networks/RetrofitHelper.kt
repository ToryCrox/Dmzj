package com.tory.dmzj.networks


import com.tory.dmzj.networks.Apis.API_RELEASE_BASE
import com.tory.dmzj.networks.Apis.API_USER_BASE
import com.tory.dmzj.networks.apis.ComicService
import com.tory.dmzj.networks.apis.UserService


import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {



    /**
     *
     * @return
     */
    val comicService: ComicService
        get() = createApi(ComicService::class.java, API_RELEASE_BASE)


    val userService: UserService
        get() = createApi(UserService::class.java, API_USER_BASE)

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private fun <T> createApi(clazz: Class<T>, baseUrl: String): T {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpManager.instance.mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(clazz)
    }


}