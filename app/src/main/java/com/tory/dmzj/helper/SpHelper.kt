package com.tory.dmzj.helper

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.tory.dmzj.MApplication
import com.tory.dmzj.bean.UserModel

/**
 * Created by tory on 2017/12/3.
 */
object SpHelper {

    val PREFERENCE_FILE  = "dmzj_data"
    public fun getSp(): SharedPreferences{
        return MApplication.instance.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
    }

    public fun setUser(userModel: UserModel){
        getSp().edit().putString("user", Gson().toJson(userModel.data)).apply()
    }

    public fun getUser() : UserModel.User?{
        val sp = getSp()
        return if(sp.contains("user")){
            Gson().fromJson(sp.getString("user" , ""), UserModel.User::class.java)
        }else{
            null
        }
    }

}