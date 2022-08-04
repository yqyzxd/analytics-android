package com.wind.analytics

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Copyright (C), 2015-2022, 杭州迈优文化创意有限公司
 * FileName: SecActivity
 * Author: wind
 * Date: 2022/8/4 12:06
 * Description: 描述该类的作用
 * Path: 路径
 * History:
 *  <author> <time> <version> <desc>
 *
 */
class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn).setOnClickListener {
            toThirdAct()
        }
    }
    private fun toThirdAct(){
        MEvent.onEvent("click_third_act")
        startActivity(Intent(this,ThirdActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        MEvent.onPageStart("third_act")
    }

    override fun onPause() {
        super.onPause()
        MEvent.onPageEnd("third_act")

    }
}