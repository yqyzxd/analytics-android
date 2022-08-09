package com.wind.analytics

import com.wind.analytics.interfaces.IUploader
import com.wind.analytics.response.MUploadEventResponse

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: EventUploader
 * Author: wind
 * Date: 2022/8/4 15:52
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class EventUploader : IUploader {
    override suspend fun upload(events: List<MEventEntity>): MUploadEventResponse {
        println("EventUploader upload events :${events}")
        Thread.sleep(500)
        return MUploadEventResponse(0)
    }
}