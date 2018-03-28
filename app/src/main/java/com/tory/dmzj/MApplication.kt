package com.tory.dmzj

import android.app.Application
import com.tory.dmzj.event.MyEventBusIndex
import com.tory.dmzj.utils.L
import org.greenrobot.eventbus.EventBus


/**
 * @Author: Tory
 * Create: 2016/9/15
 * Update: 2016/9/15
 */
class MApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        EventBus.builder().addIndex(MyEventBusIndex()).installDefaultEventBus()
        L.d("StethoReflection sourceDir=" + applicationInfo.sourceDir)
    }

    companion object {
        lateinit var instance: MApplication
            private set
    }
}
