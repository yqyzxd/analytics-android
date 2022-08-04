package com.wind.analytics

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: MEventState
 * Author: wind
 * Date: 2022/7/29 15:45
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
enum class MEventState(val value: Int) {

    INIT(0),READY(1),DONE(2),UPLOADING(3)
}