package com.tory.dmzj.networks

import com.tory.dmzj.MApplication
import com.tory.dmzj.utils.FileUtils
import com.tory.dmzj.utils.L
import com.tory.dmzj.utils.NetUtils
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by tory on 2017/12/3.
 */

internal class OkHttpManager private constructor() {

    var mOkHttpClient: OkHttpClient

    public companion object {
        public val instance = OkHttpManager()
    }

    init {
        //设置Http缓存
        val cache = Cache(File(httpCacheDir), (1024 * 1024 * 10).toLong())
        mOkHttpClient = OkHttpClient.Builder()
                .cache(cache)
                //.addInterceptor(interceptor)
                .addNetworkInterceptor(CacheInterceptor())
                .addNetworkInterceptor(DmzjInterceptor())
                //.addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
    }

    private val httpCacheDir: String
        get() = (FileUtils.getCacheDir(MApplication.instance).absolutePath
                + File.separator + "HttpCache")

    private class DmzjInterceptor:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val originalUrl = original.url()
            val url = originalUrl.newBuilder().build()
                    //.addQueryParameter("channel", "Android")
                    //.addQueryParameter("version", "2.7.0.001").build()
            val requestBuilder = original.newBuilder().url(url)
            val origUrl = originalUrl.url().toString()
            if(origUrl.startsWith(Apis.API_IMAGE_BASE_HTTPS)){
                requestBuilder.header("Referer", Apis.API_IMAGE_BASE_HTTPS)
            }else if(origUrl.startsWith(Apis.API_IMAGE_BASE)){
                requestBuilder.header("Referer", Apis.API_IMAGE_BASE)
            }

            val request = requestBuilder.build()

            return chain.proceed(request)
        }

    }
    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private class CacheInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            // 有网络时 设置缓存超时时间1个小时
            val maxAge = 60 * 60
            // 无网络时，设置超时为1天
            val maxStale = 60 * 60 * 24
            var request = chain.request()
            L.d("url=" + request.url())
            request = if (NetUtils.isNetworkAvailable(MApplication.instance)) {
                //有网络时只从网络获取
                request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build()
            } else {
                //无网络时只从缓存中读取
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            var response = chain.proceed(request)
            response = if (NetUtils.isNetworkAvailable(MApplication.instance)) {
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build()
            } else {
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build()
            }
            return response
        }
    }


}
