package com.wind.analytics

import android.app.Application
import android.content.Intent
import com.wind.analytics.interfaces.IProvider

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: App
 * Author: wind
 * Date: 2022/8/4 12:05
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val enabled = true
        val config = Config
            .newBuilder()
            .saveCompletedEvent(true)
            .userProvider(object : IProvider<String> {
                override fun provide(): String {
                    return "61266"
                }
            })
            .enabled(enabled)
            .uploader(EventUploader())
            .build()
        MEvent.install(this, config)


        if (enabled)
            MEvent.openDebug()
    }
}