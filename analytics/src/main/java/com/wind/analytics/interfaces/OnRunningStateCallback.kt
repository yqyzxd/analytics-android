package com.wind.analytics.interfaces

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: OnBackgroundCallback
 * Author: wind
 * Date: 2022/8/19 11:05
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
interface OnRunningStateCallback {
    fun onForeground()
    fun onBackground()
}