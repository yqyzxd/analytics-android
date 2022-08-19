package com.wind.analytics

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: AppDatabase
 * Author: wind
 * Date: 2022/2/24 9:29 上午
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *  作者姓名  修改时间   版本号     描述
 *
 */
@Database(entities = [MEventEntity::class],version = 1,exportSchema = false)
abstract class MEventDatabase :RoomDatabase(){

    abstract fun eventDao():MEventDao

    companion object{

        @Volatile
        private var INSTANCE:MEventDatabase?=null

        fun getInstance(context: Context):MEventDatabase{
            return INSTANCE?: synchronized(this){
                INSTANCE?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context:Context):MEventDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                MEventDatabase::class.java,
                "mu_event_db"
            ).build()
        }
    }
}