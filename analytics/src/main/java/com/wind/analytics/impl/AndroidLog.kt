package com.wind.analytics.impl

import android.util.Log
import com.wind.mlog.ALog

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: AndroidLog
 * Author: wind
 * Date: 2022/8/19 21:32
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class AndroidLog : ALog() {

    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        if (!mEnabled) {
            return
        }


        when (priority) {
            Log.VERBOSE ->Log.v(tag,message,t)
            Log.DEBUG -> Log.d(tag,message,t)
            Log.INFO -> Log.i(tag,message,t)
            Log.WARN -> Log.w(tag,message,t)
            Log.ERROR -> Log.e(tag,message,t)
            Log.ASSERT -> ""
        }


    }
}