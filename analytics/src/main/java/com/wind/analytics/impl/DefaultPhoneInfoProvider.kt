package com.wind.analytics.impl

import android.os.Build
import com.wind.analytics.bean.PhoneInfo
import com.wind.analytics.interfaces.IProvider

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: DefaultPhoneInfoProvider
 * Author: wind
 * Date: 2022/8/9 16:12
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class DefaultPhoneInfoProvider : IProvider<PhoneInfo> {
    override fun provide(): PhoneInfo {
        //获取手机厂商 / 获取手机型号
        return PhoneInfo(Build.MANUFACTURER,Build.MODEL,Build.VERSION.RELEASE)
    }
}