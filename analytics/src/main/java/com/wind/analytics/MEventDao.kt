package com.wind.analytics

import androidx.room.*

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: MEventDao
 * Author: wind
 * Date: 2022/5/20 15:46
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
@Dao
interface MEventDao {
    @Query("SELECT * FROM mu_analytics")
    suspend fun findAll():List<MEventEntity>

    @Query("SELECT * FROM mu_analytics LIMIT :count")
    suspend fun findLimit(count:Int):List<MEventEntity>

    @Query("SELECT * FROM mu_analytics WHERE id = :id")
    suspend fun findById(id:Int):MEventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: MEventEntity)

    @Delete
    suspend fun delete(event: MEventEntity)

    @Query("select count(*) from mu_analytics")
    suspend fun count():Int

    @Update
    suspend fun update(event: MEventEntity)
}