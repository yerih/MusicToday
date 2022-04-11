package com.architectcoders.musictoday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.architectcoders.musictoday.R
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.addLogAdapter(AndroidLogAdapter())
        setContentView(R.layout.activity_nav_host)
    }
}