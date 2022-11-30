package com.wind.analytics

import androidx.room.*

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: SynchronizedDao
 * Author: wind
 * Date: 2022/11/22 14:45
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class SynchronizedDao constructor(private val dao: MEventDao) {

    fun findAll(): List<MEventEntity> {
        synchronized(this) {
            try {
                return dao.findAll()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return mutableListOf()
        }
    }

    fun findByState(state: Int): List<MEventEntity> {
        synchronized(this) {
            try {
                return dao.findByState(state)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return mutableListOf()
        }
    }

    fun findLimit(count: Int): List<MEventEntity> {
        synchronized(this) {
            try {
                return dao.findLimit(count)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return mutableListOf()
        }
    }


    fun findById(id: Int): MEventEntity? {
        synchronized(this) {
            try {
                return dao.findById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    fun insert(event: MEventEntity): Long {
        synchronized(this) {
            try {
                return dao.insert(event)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return 0
        }
    }

    fun delete(vararg events: MEventEntity) {
        synchronized(this) {
            try {
                return dao.delete(*events)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun deleteCompleteEvents() {
        synchronized(this) {
            try {
                dao.deleteCompleteEvents()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun count(): Int {
        synchronized(this) {
            try {
                return dao.count()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return 0
        }
    }


    fun countByState(state: Int): Int {
        synchronized(this) {
            try {
                return dao.countByState(state)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return 0
        }
    }


    fun update(vararg events: MEventEntity): Int {
        synchronized(this) {
            try {
                return dao.update(*events)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return 0
        }
    }

}