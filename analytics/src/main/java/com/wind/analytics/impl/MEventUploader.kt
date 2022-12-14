package com.wind.analytics.impl

import android.content.Context
import com.wind.analytics.*
import com.wind.analytics.interfaces.IUploader
import com.wind.mlog.ALog
import kotlinx.coroutines.*

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: MEventUploader
 * Author: wind
 * Date: 2022/7/29 17:41
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class MEventUploader(private val context:Context,
                     private val mEventDao:SynchronizedDao,
                     private val realUploader: IUploader,
                     private val saveCompletedEvent:Boolean,
                     private val logger: ALog) :
    Queue.OnEventListener {
    private val mScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
   // private val mEventDao: MEventDao = MEventDatabase.getInstance(context).eventDao()
    private var mUploading = false


    override fun onEvent(event: Queue.EventType) {
        if (event != Queue.EventType.UPLOAD || mUploading){
            return
        }
        mUploading= true
        mScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val events=mEventDao.findByState(MEventState.READY.ordinal)
                    if (events.isNotEmpty()){
                        events.forEach { e->
                            e.state = MEventState.UPLOADING.value
                        }
                        mEventDao.update(*events.toTypedArray())

                        val response=realUploader.upload(events)
                        logger.d("MEventDispatcher","MEventUploader upload return code :${response.code} event size:${events.size}")

                        events.forEach { e->
                            e.state = MEventState.DONE.value
                        }
                        mEventDao.update(*events.toTypedArray())
                        if (!saveCompletedEvent){
                            //delete
                            //mEventDao.delete(*events.toTypedArray())
                            mEventDao.deleteCompleteEvents()
                        }

                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    logger.d("MEventUploader upload err occur :",e)
                }finally {
                    mUploading= false
                }


            }



        }


    }
    fun dispose() {
        mScope.cancel()
    }

}