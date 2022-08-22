package com.wind.analytics

import android.content.Context
import com.wind.analytics.bean.AppInfo
import com.wind.analytics.bean.PhoneInfo
import com.wind.analytics.impl.AppInfoProvider
import com.wind.analytics.impl.DefaultPhoneInfoProvider
import com.wind.analytics.interfaces.IProvider
import org.json.JSONObject

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: EventBuilder
 * Author: wind
 * Date: 2022/8/19 11:57
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class EventBuilder(
    private var mContext: Context,
    private var mUserInfoProvider: IProvider<String>?,
    private var mChannel: String
) {


    private var mPhoneInfo: PhoneInfo = DefaultPhoneInfoProvider().provide()
    private var mAppInfo: AppInfo

    init {

        //获取app版本号  和 渠道号
        val provider = AppInfoProvider(mContext, mChannel)
        mAppInfo = provider.provide()


    }

    fun filter():Boolean{
        val uid = mUserInfoProvider?.provide() ?: ""
        return uid.isNullOrEmpty()
    }

    fun build(
        key: String,
        state: MEventState,
        timestamp: Long,
        type: MEventType,
        ext: Map<String, String>? = null
    ): MEventEntity {

        val uid = mUserInfoProvider?.provide() ?: ""

        val extJson = if (ext.isNullOrEmpty().not()) {
            val jsonObject = JSONObject(ext)
            jsonObject.toString()
        } else ""


        var phone = "${mPhoneInfo.manufacture}/${mPhoneInfo.model}"
        var version = mPhoneInfo.osVersion

        return MEventEntity(
            eventId = key,
            uid = uid,
            state = state.value,
            begin = timestamp,
            end = timestamp,
            type = type.value,
            ext = extJson,
            phone = phone,
            version = version,
            appVersion = mAppInfo.versionName,
            channel = mAppInfo.channel
        )
    }
}