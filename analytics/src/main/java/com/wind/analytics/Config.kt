package com.wind.analytics

import com.wind.analytics.impl.NullUploader
import com.wind.analytics.interfaces.IProvider
import com.wind.analytics.interfaces.IUploader
import com.wind.analytics.interfaces.OnRunningStateCallback

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: Config
 * Author: wind
 * Date: 2022/7/29 15:22
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 * @param intervalSec 间隔interval上传一次数据
 * @param durationIntervalSec 间隔durationInterval更新duration event's end time
 * @param threshold 记录超过threshold时上传一次数据
 */
class Config() {
    var intervalSec: Long = INTERVAL_SEC
    var durationIntervalSec: Long = DURATION_INTERVAL_SEC
    var threshold: Int = THRESHOLD

    var channel: String = ""
    var saveCompletedEvent = false

    var userProvider: IProvider<String>? = null
    var uploader: IUploader = NullUploader()
    var callback: OnRunningStateCallback? = null

    var enabled =true
    constructor(builder: Builder) : this() {
        this.intervalSec = builder.intervalSec
        this.durationIntervalSec = builder.durationIntervalSec
        this.threshold = builder.threshold
        this.channel = builder.channel
        this.userProvider = builder.userProvider
        this.callback = builder.callback
        this.saveCompletedEvent=builder.saveCompletedEvent
        this.enabled=builder.enabled
        if (builder.uploader != null) {
            this.uploader = builder.uploader!!
        }

    }


    class Builder {
        var intervalSec: Long = INTERVAL_SEC
        var durationIntervalSec: Long = DURATION_INTERVAL_SEC
        var threshold: Int = THRESHOLD
        var userProvider: IProvider<String>? = null
        var uploader: IUploader? = null
        var callback: OnRunningStateCallback? = null
        var channel: String = ""
        var saveCompletedEvent = false
        var enabled =true

        fun interval(intervalSec: Long): Builder {
            this.intervalSec = intervalSec
            return this
        }

        fun durationInterval(durationIntervalSec: Long): Builder {
            this.durationIntervalSec = durationIntervalSec
            return this
        }

        fun threshold(threshold: Int): Builder {
            this.threshold = threshold
            return this
        }

        fun userProvider(userProvider: IProvider<String>): Builder {
            this.userProvider = userProvider
            return this
        }

        fun uploader(uploader: IUploader): Builder {
            this.uploader = uploader
            return this
        }

        fun backgroundCallback(callback: OnRunningStateCallback): Builder {
            this.callback = callback
            return this
        }

        fun channel(channel: String): Builder {
            this.channel = channel
            return this
        }

        fun saveCompletedEvent(save:Boolean):Builder{
            this.saveCompletedEvent=save
            return this
        }

        fun enabled(enabled:Boolean):Builder{
            this.enabled=enabled
            return this
        }

        fun build(): Config {
            return Config(this)
        }
    }


    companion object {
        const val INTERVAL_SEC = 15L
        const val DURATION_INTERVAL_SEC = 15L
        const val THRESHOLD = 100

        @JvmStatic
        fun newBuilder(): Builder {
            return Builder()
        }

    }
}



