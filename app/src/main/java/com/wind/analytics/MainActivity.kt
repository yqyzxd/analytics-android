package com.wind.analytics

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn).setOnClickListener {
            toSecAct()
        }

        println("Build.VERSION.SDK_INT:${Build.VERSION.SDK_INT}")
        println(" Build.VERSION.RELEASE:${ Build.VERSION.RELEASE}")
    }

    private fun toSecAct(){
        MEvent.onEvent("click_main_act")
        startActivity(Intent(this,SecActivity::class.java))
    }


    override fun onResume() {
        super.onResume()
        MEvent.onPageStart("main_act")
    }

    override fun onPause() {
        super.onPause()
        MEvent.onPageEnd("main_act")

    }
}