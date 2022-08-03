package com.wind.analytics

import java.util.*
import java.util.concurrent.LinkedBlockingDeque

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: MEventUploader
 * Author: wind
 * Date: 2022/7/29 17:41
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class MEventUploader(val mEventDao:MEventDao) : Queue.OnEventListener {

    override fun onEvent(event: Queue.EventType) {
        if (event != Queue.EventType.UPLOAD){
            return
        }
        //todo 执行上传
        println("upload events")

    }


}