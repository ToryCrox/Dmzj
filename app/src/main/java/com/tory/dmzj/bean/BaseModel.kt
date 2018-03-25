package com.tory.dmzj.bean

import android.os.Parcel

import java.util.HashMap

open class BaseModel {
    var tag: Any? = null
    protected var mTags: MutableMap<Int, Any>? = null

    fun setTag(key: Int, tag: Any) {
        if (this.mTags == null) {
            this.mTags = HashMap()
        }
        this.mTags!!.put(Integer.valueOf(key), tag)
    }

    fun getTag(key: Int): Any? {
        return if (this.mTags != null && this.mTags!!.containsKey(Integer.valueOf(key))) {
            this.mTags!![Integer.valueOf(key)]
        } else null
    }

    fun superWriteToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(this.tag)
        dest.writeMap(this.mTags)
    }

    companion object {

        fun superCreateFromParcel(bean: BaseModel, `in`: Parcel) {
            bean.tag = `in`.readValue(Any::class.java.classLoader)
            //bean.mTags = `in`.readHashMap(HashMap<*, *>::class.java.classLoader)
        }
    }
}