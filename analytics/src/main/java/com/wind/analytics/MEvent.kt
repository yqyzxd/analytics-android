package com.wind.analytics

import android.content.Context
import com.wind.mlog.ALog
import com.wind.mlog.MLog

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
class MEvent {

    private lateinit var mAppContext: Context
    private var sDebug = false
    private var sLogger: ALog = MLog.getDefault()
    private lateinit var sDispatcher: MEventDispatcher
    private var mUserInfoProvider: UserInfoProvider? = null

    fun install(context: Context) {
        mAppContext = context.applicationContext
        sDispatcher = MEventDispatcher(mAppContext)
    }

    fun registerUserInfoProvider(provider: UserInfoProvider) {
        mUserInfoProvider = provider
    }

    /**
     * 记录点击事件
     */
    fun onEvent(key: String) {
        sDispatcher.dispatch(buildEvent(key,MEventState.READY,System.currentTimeMillis(),MEventType.CLICK))
    }



    /**
     * 开启调试模式
     */
    fun openDebug() {
        sDebug = true
    }

    /**
     * 统计时长开始事件
     */
    fun onPageStart(key: String) {
        sDispatcher.dispatch(buildEvent(key,MEventState.INIT,System.currentTimeMillis(),MEventType.DURATION))
    }

    /**
     * 统计时长结束事件
     */
    fun onPageEnd(key: String) {
        sDispatcher.dispatch(buildEvent(key,MEventState.READY,System.currentTimeMillis(),MEventType.DURATION))
    }




    private fun buildEvent(key: String,state:MEventState,timestamp: Long,type:MEventType): MEventEntity {
        val uid = mUserInfoProvider?.provideUserInfo() ?: ""

        return MEventEntity(
            eventId = key,
            uid = uid,
            state = state.ordinal,
            begin =timestamp,
            end =  timestamp,
            type = type.ordinal
        )

    }

}