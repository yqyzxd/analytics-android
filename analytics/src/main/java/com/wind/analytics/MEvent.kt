package com.wind.analytics

import android.content.Context
import com.wind.analytics.impl.DefaultPhoneInfoProvider
import com.wind.analytics.interfaces.IUserInfoProvider
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

    private lateinit var mAppContext: Context
    private lateinit var sDispatcher: MEventDispatcher
    private var mUserInfoProvider: IUserInfoProvider? = null
    private var mPhoneModel: String = ""

    fun install(context: Context, config: Config) {
        mAppContext = context.applicationContext
        sDispatcher = MEventDispatcher(mAppContext, config)
        mUserInfoProvider = config.userProvider


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
        val uid = mUserInfoProvider?.provideUserInfo() ?: ""

        val extJson = if (ext.isNullOrEmpty().not()) {
            val jsonObject = JSONObject(ext)
            jsonObject.toString()
        } else ""

        if (mPhoneModel.isNullOrEmpty()){
            val provider= DefaultPhoneInfoProvider()
            mPhoneModel = provider.providePhoneInfo()
        }


        return MEventEntity(
            eventId = key,
            uid = uid,
            state = state.value,
            begin = timestamp,
            end = timestamp,
            type = type.value,
            ext = extJson,
            phone = mPhoneModel
        )

    }

}