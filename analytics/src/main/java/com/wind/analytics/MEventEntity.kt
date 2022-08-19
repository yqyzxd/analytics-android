package com.wind.analytics

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: MEventEntity
 * Author: wind
 * Date: 2022/7/29 14:02
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
@Entity(tableName = "mu_analytics")
data class MEventEntity(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    @ColumnInfo(name = "event_id") val eventId:String,
    @ColumnInfo val uid:String,
    @ColumnInfo var state:Int,// 事件状态（0未就绪/1已就绪/2已上传）
    @ColumnInfo val begin:Long, //时长事件开始时间
    @ColumnInfo val end:Long, //时长事件结束时间
    @ColumnInfo val type:Int, //事件类型 （0点击事件 / 1 时长事件）
    @ColumnInfo val ext:String ="", //该事件的额外信息 如有值必须为json格式 目前增加to_uid
    @ColumnInfo val os:String ="android",
    @ColumnInfo val phone:String ="", //手机具体型号
    @ColumnInfo val version:String ="", //手机操作系统版本
    @ColumnInfo(name = "app_version") val appVersion:String ="", //app版本
    @ColumnInfo val channel:String ="" //渠道号

)
