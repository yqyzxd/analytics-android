package com.wind.analytics

import com.wind.analytics.interfaces.OnRunningStateCallback

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: ActivityStack
 * Author: wind
 * Date: 2022/8/19 11:08
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
object ActivityStack {
    @Volatile
    private var mCount:Int=0


    private val mCallbacks = mutableSetOf<OnRunningStateCallback>()


    fun registerOnBackgroundCallback(callback: OnRunningStateCallback){
        mCallbacks.add(callback)
    }

    fun onStart(){
        if (mCount==0){
            mCallbacks.forEach {
                it.onForeground()
            }
        }
        mCount++
    }
    fun onStop(){
        mCount--

        if (mCount==0){
            mCallbacks.forEach {
                it.onBackground()
            }
        }
    }

    fun getSize() = mCount
}