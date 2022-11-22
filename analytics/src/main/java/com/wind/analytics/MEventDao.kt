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
    fun findAll():List<MEventEntity>

    @Query("SELECT * FROM mu_analytics WHERE state = :state")
    fun findByState(state:Int):List<MEventEntity>

    @Query("SELECT * FROM mu_analytics LIMIT :count")
    fun findLimit(count:Int):List<MEventEntity>

    @Query("SELECT * FROM mu_analytics WHERE id = :id")
    fun findById(id:Int):MEventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: MEventEntity) :Long

    @Delete
    fun delete(vararg events: MEventEntity)

    @Query("DELETE FROM mu_analytics WHERE state = 2")
    fun deleteCompleteEvents()

    @Query("SELECT count(*) FROM mu_analytics")
    fun count():Int

    @Query("SELECT count(*) FROM mu_analytics WHERE state = :state")
    fun countByState(state:Int):Int

    @Update
    fun update(vararg events: MEventEntity):Int

}