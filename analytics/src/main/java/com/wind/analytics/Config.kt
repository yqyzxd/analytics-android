package com.wind.analytics

import com.wind.analytics.impl.NullUploader
import com.wind.analytics.interfaces.IUploader
import com.wind.analytics.interfaces.IUserInfoProvider

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

    var userProvider: IUserInfoProvider? = null
    var uploader: IUploader = NullUploader()

    constructor(builder: Builder) : this() {
        this.intervalSec = builder.intervalSec
        this.durationIntervalSec = builder.durationIntervalSec
        this.threshold = builder.threshold

        this.userProvider = builder.userProvider

        if (builder.uploader != null) {
            this.uploader = builder.uploader!!
        }

    }



    class Builder {
        var intervalSec: Long = INTERVAL_SEC
        var durationIntervalSec: Long = DURATION_INTERVAL_SEC
        var threshold: Int = THRESHOLD
        var userProvider: IUserInfoProvider? = null
        var uploader: IUploader? = null

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

        fun userProvider(userProvider: IUserInfoProvider): Builder {
            this.userProvider = userProvider
            return this
        }

        fun uploader(uploader: IUploader): Builder {
            this.uploader = uploader
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



