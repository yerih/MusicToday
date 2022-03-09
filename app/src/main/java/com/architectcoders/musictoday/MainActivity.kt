package com.architectcoders.musictoday


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.architectcoders.musictoday.databinding.ActivityMainBinding
import com.architectcoders.musictoday.model.LastFmService
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bi.root)
        lifecycleScope.launch {
            val artists = LastFmService.service.artistOfToday()
            Log.i("TGB", "${artists.artists.artist}")
        }
    }
}