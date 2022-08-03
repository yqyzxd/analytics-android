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

    init {
        mHandlerThread.start()
        mHandler = Handler(mHandlerThread.looper)

        startTicker()
    }

    private fun startTicker() {
        mHandler.postDelayed(mTickerRunnable,interval)
    }

    fun addIntervalListener(lis :OnIntervalListener){
        mListeners.add(lis)
    }


    inner class TickerRunnable : Runnable{
        override fun run() {
            //执行操作
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