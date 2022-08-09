package com.wind.analytics.interfaces

import com.wind.analytics.MEventEntity
import com.wind.analytics.response.MUploadEventResponse

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: IUploader
 * Author: wind
 * Date: 2022/8/4 15:30
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
interface IUploader {
    suspend fun upload(events:List<MEventEntity>): MUploadEventResponse
}