package com.wind.analytics

import android.os.Handler
import android.os.HandlerThread

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: Ticker
 * Author: wind
 * Date: 2022/7/29 16:12
 * Description: 定时器
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class Ticker(val interval:Long) {

    private val mHandlerThread = HandlerThread("analytics_ticker")
    private val mHandler : Handler
    private val mTickerRunnable = TickerRunnable()
    private val mListeners = mutableSetOf<OnIntervalListener>()
    private var mStarted = false
    init {
        mHandlerThread.start()
        mHandler = Handler(mHandlerThread.looper)

        startTicker()
    }

    fun startTicker() {
        if (!mStarted){
            mHandler.postDelayed(mTickerRunnable,interval)
            mStarted=true
        }

    }
    fun stopTicker(){
        mHandler.removeCallbacksAndMessages(null)
        mStarted=false
    }

    fun addIntervalListener(lis :OnIntervalListener){
        mListeners.add(lis)
        if (!mStarted){
            startTicker()
        }
    }

    fun removeIntervalListener(lis :OnIntervalListener){
        mListeners.remove(lis)

        if (mListeners.isEmpty()){
            stopTicker()
        }
    }

    inner class TickerRunnable : Runnable{
        override fun run() {
            mListeners.forEach {
                it.onInterval()
            }
            mHandler.postDelayed(this,interval)
        }
    }

    interface OnIntervalListener{
        fun onInterval()
    }

}