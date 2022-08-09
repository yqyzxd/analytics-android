package com.wind.analytics

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: MapToJsonTest
 * Author: wind
 * Date: 2022/8/9 15:36
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
@RunWith(AndroidJUnit4::class)
class MapToJsonTest {
    @Test
    fun testMapToJson() {
        val ext = mutableMapOf<String, Any?>()
        ext["uid"] = "51985"
        ext["name"] = "wind"
        val extJson = if (ext.isNullOrEmpty().not()) {
            val jsonObject = JSONObject(ext)
            jsonObject.toString()
        } else ""

        val expected = getExpected(ext)
        Assert.assertEquals("want $expected but got $extJson",expected,extJson)

    }

    private fun getExpected(map: Map<String, Any?>): String {
        val jsonBuilder = StringBuilder()
        jsonBuilder.append("{")
        map.onEachIndexed { index, entry ->
            jsonBuilder
                .append("\"")
                .append(entry.key)
                .append("\"")
                .append(":")
                .append("\"")
                .append(entry.value)
                .append("\"")
            if (index != map.size - 1) {
                jsonBuilder.append(",")
            }
        }
        jsonBuilder.append("}")
        return jsonBuilder.toString()
    }
}