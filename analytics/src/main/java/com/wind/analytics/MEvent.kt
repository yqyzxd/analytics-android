package com.wind.analytics

import android.content.Context

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

    private lateinit var mAppContext: Context

    private lateinit var sDispatcher: MEventDispatcher
    private var mUserInfoProvider: IUserInfoProvider? = null

    fun install(context: Context,config: Config) {
        mAppContext = context.applicationContext
        sDispatcher = MEventDispatcher(mAppContext,config)
        mUserInfoProvider=config.userProvider
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
        sDispatcher.enableLog()

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