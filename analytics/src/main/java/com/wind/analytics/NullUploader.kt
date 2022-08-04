package com.wind.analytics

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: NullUploader
 * Author: wind
 * Date: 2022/8/4 16:16
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class NullUploader :IUploader {
    override suspend fun upload(events: List<MEventEntity>): MUploadEventResponse {
        return MUploadEventResponse(0)
    }
}