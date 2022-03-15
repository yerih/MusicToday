package com.architectcoders.musictoday.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.architectcoders.musictoday.databinding.ActivityMainBinding
import com.architectcoders.musictoday.model.MusicService
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
//    private var adapter = ArtistAdapter{
//        val i = Intent(this, DetailActivity::class.java)
//        i.putExtra(DetailActivity.ARTIST, it)
//        startActivity(i)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bi.root)
        lifecycleScope.launch {
            val artists = MusicService.service.getPopularArtists().artists
            Log.i("TGB", "$artists")

            val adapter = ArtistAdapter{
                val i = Intent(applicationContext, DetailActivity::class.java)
                i.putExtra(DetailActivity.ARTIST, it)
                startActivity(i)
            }
            adapter.artists = artists
            bi.recycler.adapter = adapter
        }
    }
}