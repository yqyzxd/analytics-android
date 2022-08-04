package com.wind.analytics

import android.app.Application

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

        val config = Config
            .newBuilder()
            .userProvider(object : IUserInfoProvider {
                override fun provideUserInfo(): String {
                    return "61266"
                }
            })
            .uploader(EventUploader())
            .build()
        MEvent.install(this, config)



        MEvent.openDebug()
    }
}