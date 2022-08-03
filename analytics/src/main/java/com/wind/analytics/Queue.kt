package com.wind.analytics

import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: Queue
 * Author: wind
 * Date: 2022/7/29 17:49
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
object Queue {

    private var mQueue = LinkedBlockingQueue<EventType>()
    private val mListeners = mutableSetOf<OnEventListener>()
    private var mQuit = false

    init {
        thread {
            while (!mQuit) {
                try {
                    val event = mQueue.take()
                    mListeners.forEach {
                        it.onEvent(event)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }
        }


    }

    fun enqueue(event: EventType) {
        mQueue.offer(event)
    }


    fun register(listener: OnEventListener) {
        mListeners.add(listener)
    }

    fun quit() {
        mQuit = true
    }

    interface OnEventListener {
        fun onEvent(event: EventType)
    }
    enum class EventType{
        UPLOAD
    }
}