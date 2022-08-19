package com.wind.analytics

import android.app.Application
import com.wind.analytics.impl.AndroidLog
import com.wind.analytics.impl.MEventUploader
import com.wind.analytics.impl.SystemOutLog
import com.wind.analytics.interfaces.OnRunningStateCallback
import com.wind.mlog.ALog
import com.wind.mlog.MLog
import kotlinx.coroutines.*


/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: MEventDispatcher
 * Author: wind
 * Date: 2022/7/29 14:42
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class MEventDispatcher(private val application: Application,private val mConfig: Config) : Ticker.OnIntervalListener {
    private val mScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val mDao: MEventDao = MEventDatabase.getInstance(application.applicationContext).eventDao()

    private val mKeyMap = mutableMapOf<String, Int>()

    private val mUploadTicker = Ticker(mConfig.intervalSec * 1000)

    private val mDurationEventTicker = Ticker(mConfig.durationIntervalSec * 1000)

    private val mListenerMap = mutableMapOf<String, Ticker.OnIntervalListener?>()

    private val mQueue = Queue

    private var mLogger: ALog = MLog()

    private val mEventUploader = MEventUploader(application.applicationContext,mConfig.uploader,mConfig.saveCompletedEvent,mLogger)
    private val mActivityLifecycleCallback = ActivityLifecycleCallback(application)

    init {
        mUploadTicker.addIntervalListener(this)
        mQueue.register(mEventUploader)
        (mLogger as MLog).register(AndroidLog())
        mLogger.setEnabled(false)

        mActivityLifecycleCallback.registerOnBackgroundCallback(object :OnRunningStateCallback{
            override fun onBackground() {
                mLogger.d("MEventDispatcher","onBackground")
                stopTicker()
                onInterval()
            }

            override fun onForeground() {
                startTicker()
            }

        })
        //发送init事件
        dispatch(
            MEventEntity(
                type = MEventType.INIT.value,
                uid = "",
                state = 0,
                begin = System.currentTimeMillis(),
                end = System.currentTimeMillis(),
                eventId = ""
            )
        )
    }


    private fun startTicker() {
        mLogger.d(TAG,"startTicker")
        mUploadTicker.startTicker()
    }

    private fun stopTicker() {
        mLogger.d(TAG,"stopTicker")
        mUploadTicker.stopTicker()
    }

    fun dispatch(event: MEventEntity) {
        startOrStopDurationTicker(event)

        mScope.launch(Dispatchers.IO) {
            when (event.type) {
                MEventType.CLICK.value -> {
                    mDao.insert(event)
                }
                MEventType.DURATION.value -> {
                    when (event.state) {
                        MEventState.INIT.value -> {
                            onDurationEventInit(event)
                        }
                        MEventState.READY.value -> {
                            onDurationEventReady(event)
                        }
                        MEventState.DONE.value -> {
                        }
                    }
                }
                MEventType.INIT.value->{
                    //将数据库中state为0的所有事件改为state为1
                    val events=mDao.findByState(MEventState.INIT.value)
                    if (events.isNullOrEmpty().not()){
                        events.forEach {
                            it.state = MEventState.READY.value
                        }
                        val updateCount=mDao.update(*events.toTypedArray())
                        mLogger.d("MEventType.INIT updateCount:${updateCount}")
                    }

                }
            }
            //检查是否大于
            var count = mDao.countByState(MEventState.READY.value)
            if (count > mConfig.threshold) {
                //上传
                mQueue.enqueue(Queue.EventType.UPLOAD)
            }
        }
    }

    private suspend fun onDurationEventInit(event: MEventEntity) {
        mLogger.d(TAG,"Enter onDurationEventInit")
        val id = mDao.insert(event)
        mLogger.d(TAG,"onDurationEventInit generated id :$id")
        //存入id
        mKeyMap[event.eventId] = id.toInt()
        mLogger.d(TAG,"Exit onDurationEventInit")

    }

    private suspend fun onDurationEventReady(event: MEventEntity) {
        mLogger.d(TAG,"Enter onDurationEventReady")
        val id = mKeyMap[event.eventId]

        if (id == null) {
            mLogger.e(TAG,"onDurationEventReady id is null !!!")
        } else {
            mDao.findById(id)?.apply {
                val newEvent = updateEventWithStateAndEnd(this, event)
                mDao.update(newEvent)
                mLogger.d(TAG,"onDurationEventReady update event success")
            }
        }
        mKeyMap.remove(event.eventId)
        mLogger.d(TAG,"Exit onDurationEventReady")
    }

    private fun startOrStopDurationTicker(event: MEventEntity) {

        when (event.type) {

            MEventType.DURATION.value -> {
                mLogger.d(TAG,"Enter startOrStopDurationTicker for ${event.eventId} ${event.ext}")
                when (event.state) {
                    MEventState.INIT.value -> {
                        //启动duration_ticker
                        var listener = mListenerMap[event.eventId]
                        if (listener == null) {
                            listener = DurationListener(event.eventId)
                        }
                        mLogger.d(TAG,"Exit startOrStopDurationTicker addIntervalListener")
                        mDurationEventTicker.addIntervalListener(listener)
                        mListenerMap[event.eventId] = listener

                    }
                    MEventState.READY.value -> {
                        //结束duration_ticker
                        var listener = mListenerMap[event.eventId]
                        if (listener != null) {
                            mLogger.d(TAG,"Exit startOrStopDurationTicker removeIntervalListener")
                            mDurationEventTicker.removeIntervalListener(listener)
                        }
                        mListenerMap.remove(event.eventId)
                    }
                }
                mLogger.d(TAG,"Exit startOrStopDurationTicker mListenerMap.size:${mListenerMap.size}")
            }
        }

    }


    private fun updateEventWithStateAndEnd(exist: MEventEntity, event: MEventEntity): MEventEntity {
       return exist.ofEnd(event.end).ofState(event.state)

    }

    private inner class DurationListener(private val eventId: String) : Ticker.OnIntervalListener {
        override fun onInterval() {
            //更新eventId 对应的duration时间的end time
            mLogger.d(TAG,"Enter DurationListener onInterval")
            //查询出eventId并且状态为init的事件更新其 end time 为当前时间
            mScope.launch(Dispatchers.IO) {
                val id = mKeyMap[eventId]

                if (id == null) {
                    //log
                    mLogger.e(TAG,"DurationListener onInterval id is null !!!")
                } else {
                    mDao.findById(id)?.let { exist ->
                        if (exist.state == MEventState.INIT.value) {
                            val newEvent = exist.ofEnd(System.currentTimeMillis())
                            mLogger.d(TAG,"DurationListener onInterval update:${newEvent}")
                            mDao.update(newEvent)
                        }
                    }
                }
                mLogger.d(TAG,"Exit DurationListener onInterval")
            }

        }


    }


    fun dispose() {
        mLogger.d("MEventDispatcher dispose")
        mScope.cancel()
        mEventUploader.dispose()
    }





    override fun onInterval() {
        mLogger.d(TAG,"MEventDispatcher onInterval UPLOAD")
        mQueue.enqueue(Queue.EventType.UPLOAD)
    }

    fun enableLog() {
        mLogger.setEnabled(true)
    }


    companion object{
        val TAG =  "MEventDispatcher"
    }
}