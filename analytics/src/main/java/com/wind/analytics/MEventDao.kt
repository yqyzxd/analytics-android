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

    @Query("SELECT * FROM mu_analytics WHERE state = :state")
    suspend fun findByState(state:Int):List<MEventEntity>

    @Query("SELECT * FROM mu_analytics LIMIT :count")
    suspend fun findLimit(count:Int):List<MEventEntity>

    @Query("SELECT * FROM mu_analytics WHERE id = :id")
    suspend fun findById(id:Int):MEventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: MEventEntity) :Long

    @Delete
    suspend fun delete(vararg events: MEventEntity)

    @Query("DELETE FROM mu_analytics WHERE state = 2")
    suspend fun deleteCompleteEvents()

    @Query("SELECT count(*) FROM mu_analytics")
    suspend fun count():Int

    @Query("SELECT count(*) FROM mu_analytics WHERE state = :state")
    suspend fun countByState(state:Int):Int

    @Update
    suspend fun update(vararg events: MEventEntity):Int

}