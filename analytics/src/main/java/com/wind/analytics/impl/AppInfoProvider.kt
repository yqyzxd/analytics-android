package com.wind.analytics.impl

import android.content.Context
import com.wind.analytics.bean.AppInfo
import com.wind.analytics.interfaces.IProvider

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: AppInfoProvider
 * Author: wind
 * Date: 2022/8/19 11:42
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class AppInfoProvider(private val context: Context, private val channel:String) :IProvider<AppInfo> {

    private val mVersionName:String

    init {
        mVersionName= getAppVersionName(context)
    }
    private fun getAppVersionName(context: Context): String {
        var versionName=""
        try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            versionName= if (info.versionName == null) "" else info.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionName
    }

    override fun provide(): AppInfo {
        return AppInfo(mVersionName,channel)
    }
}



