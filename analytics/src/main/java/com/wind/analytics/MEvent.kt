package com.wind.analytics

import android.app.Application
import android.content.Context
import com.wind.analytics.bean.AppInfo
import com.wind.analytics.bean.PhoneInfo
import com.wind.analytics.impl.DefaultPhoneInfoProvider
import com.wind.analytics.interfaces.IProvider

import org.json.JSONObject

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: MEvent
 * Author: wind
 * Date: 2022/7/29 13:53
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
object MEvent {


    private lateinit var sDispatcher: MEventDispatcher
    private lateinit var mEventBuilder: EventBuilder


    fun install(application: Application, config: Config) {
        val appContext = application.applicationContext
        sDispatcher = MEventDispatcher(application, config)
        mEventBuilder = EventBuilder(appContext, config.userProvider, config.channel)
    }


    /**
     * 记录点击事件
     */
    @JvmOverloads
    fun onEvent(key: String, ext: Map<String, String>? = null) {
        sDispatcher.dispatch(
            buildEvent(
                key,
                MEventState.READY,
                System.currentTimeMillis(),
                MEventType.CLICK,
                ext
            )
        )
    }


    /**
     * 开启调试模式
     */
    fun openDebug() {
        sDispatcher.enableLog()

    }

    /**
     * 统计时长开始事件
     */
    @JvmOverloads
    fun onPageStart(key: String, ext: Map<String, String>? = null) {
        sDispatcher.dispatch(
            buildEvent(
                key,
                MEventState.INIT,
                System.currentTimeMillis(),
                MEventType.DURATION,
                ext
            )
        )
    }

    /**
     * 统计时长结束事件
     */
    fun onPageEnd(key: String) {
        sDispatcher.dispatch(
            buildEvent(
                key,
                MEventState.READY,
                System.currentTimeMillis(),
                MEventType.DURATION
            )
        )
    }


    private fun buildEvent(
        key: String,
        state: MEventState,
        timestamp: Long,
        type: MEventType,
        ext: Map<String, String>? = null
    ): MEventEntity {

        return mEventBuilder.build(key, state, timestamp, type, ext)
    }

}