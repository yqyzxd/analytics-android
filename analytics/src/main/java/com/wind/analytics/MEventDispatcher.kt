package com.wind.analytics

import android.content.Context
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
class MEventDispatcher(private val context: Context):Ticker.OnIntervalListener{
    private val mScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val mDao: MEventDao = MEventDatabase.getInstance(context).eventDao()

    private val mConfig: Config = Config(INTERVAL, THRESHOLD)

    private val mKeyMap = mutableMapOf<String, Int>()

    private val mTicker = Ticker(mConfig.interval)

    private val mQueue = Queue

    init {
        mTicker.addIntervalListener(this)
    }

    fun dispatch(event: MEventEntity) {

        mScope.launch(Dispatchers.IO) {
            when (event.type) {
                MEventType.CLICK.ordinal -> {
                    mDao.insert(event)
                }
                MEventType.DURATION.ordinal -> {
                    when (event.state) {
                        MEventState.INIT.ordinal -> {
                            val id = mKeyMap[event.eventId]
                            if (id == null) {
                                mDao.insert(event)
                            } else {
                                mDao.findById(id)?.apply {
                                    val newEvent = updateEventWithStateAndEnd(this, event)
                                    mDao.update(newEvent)
                                }
                            }
                            //存入id
                            mKeyMap[event.eventId] = event.id
                        }
                        MEventState.READY.ordinal -> {
                            val id = mKeyMap[event.eventId]

                            if (id == null) {
                                //todo log
                            } else {
                                mDao.findById(id)?.apply {
                                    val newEvent = updateEventWithStateAndEnd(this, event)
                                    mDao.update(newEvent)
                                }
                            }
                            mKeyMap.remove(event.eventId)
                        }
                        MEventState.DONE.ordinal -> {

                        }
                    }


                }
            }

            //检查是否大于
            var count = mDao.count()
            if (count > mConfig.threshold) {
                //上传
                mQueue.enqueue(Queue.EventType.UPLOAD)
            }
        }
    }


    private fun updateEventWithStateAndEnd(exist: MEventEntity, event: MEventEntity): MEventEntity {
        return MEventEntity(
            id = exist.id,
            eventId = exist.eventId,
            uid = exist.uid,
            state = event.state,
            begin = exist.begin,
            end = event.end,
            type = exist.type
        )
    }


    fun dispose() {
        mScope.cancel()
    }


    companion object {
        const val INTERVAL = 15 * 60L
        const val THRESHOLD = 100
    }


    override fun onInterval() {
        mQueue.enqueue(Queue.EventType.UPLOAD)
    }
}