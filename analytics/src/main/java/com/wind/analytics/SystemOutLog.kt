package com.wind.analytics

import com.wind.mlog.ALog

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: SystemOutLog
 * Author: wind
 * Date: 2022/8/4 14:57
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class SystemOutLog() : ALog() {
    private var sBuilder: StringBuilder = StringBuilder()


    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        if (!mEnabled) {
            return
        }
        sBuilder.clear()

        sBuilder
            .append(getPriorityDesc(priority))
            .append(" ")
            .append(message)
        println(sBuilder.toString())
    }
}