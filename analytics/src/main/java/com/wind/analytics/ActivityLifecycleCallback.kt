package com.wind.analytics

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.wind.analytics.interfaces.OnRunningStateCallback

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: ActivityLifecycleCallback
 * Author: wind
 * Date: 2022/8/19 11:06
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class ActivityLifecycleCallback(private val mApplication: Application) :  Application.ActivityLifecycleCallbacks{


    init {
        mApplication.registerActivityLifecycleCallbacks(this)
    }
    fun registerOnBackgroundCallback(callback: OnRunningStateCallback){
        ActivityStack.registerOnBackgroundCallback(callback)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        ActivityStack.onStart()
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        ActivityStack.onStop()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}