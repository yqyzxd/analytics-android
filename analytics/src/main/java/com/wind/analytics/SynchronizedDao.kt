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
class SynchronizedDao constructor(private val dao:MEventDao) {

    fun findAll():List<MEventEntity>{
        synchronized(this){
            return  dao.findAll()
        }
    }

    fun findByState(state:Int):List<MEventEntity>{
        synchronized(this){
            return dao.findByState(state)
        }
    }

    fun findLimit(count:Int):List<MEventEntity>{
        synchronized(this){
            return dao.findLimit(count)
        }
    }


    fun findById(id:Int):MEventEntity?{
        synchronized(this){
            return dao.findById(id)
        }
    }

    fun insert(event: MEventEntity) :Long{
        synchronized(this){
            return dao.insert(event)
        }
    }

    fun delete(vararg events: MEventEntity){
        synchronized(this){
            return dao.delete(*events)
        }
    }

    fun deleteCompleteEvents(){
        synchronized(this){
            dao.deleteCompleteEvents()
        }
    }

    fun count():Int{
        synchronized(this){
           return dao.count()
        }
    }


    fun countByState(state:Int):Int{
        synchronized(this){
            return dao.countByState(state)
        }
    }


    fun update(vararg events: MEventEntity):Int{
        synchronized(this){
           return dao.update(*events)
        }
    }

}