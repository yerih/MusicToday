package com.architectcoders.musictoday.ui


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.architectcoders.musictoday.databinding.ActivityMainBinding
import com.architectcoders.musictoday.model.LastFmService
import com.architectcoders.musictoday.ui.ArtistAdapter
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var adapter = ArtistAdapter{}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bi.root)
        bi.recycler.adapter = adapter

        lifecycleScope.launch {
            val artists = LastFmService.service.artistOfToday().artists.artist
            Log.i("TGB", "${artists}")
            adapter.artists = artists
            bi.recycler.adapter = adapter
        }
    }
}