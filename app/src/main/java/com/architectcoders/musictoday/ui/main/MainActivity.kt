package com.architectcoders.musictoday.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.architectcoders.musictoday.databinding.ActivityMainBinding
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.basicDiffUtil
import com.architectcoders.musictoday.model.log
import com.architectcoders.musictoday.ui.detail.DetailActivity
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels{MainViewModelFactory()}
    private val adapter = ArtistAdapter{ viewModel.onArtistClicked(it) }
    private lateinit var bi: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bi.root)
        viewModel.state.observe(this, ::updateUI)
    }

    private fun updateUI(state: MainViewModel.UiState) {
        //TODO: loader true
        state.popularArtists?.let { it ->
            adapter.artists = it.artists
            if(bi.recycler.adapter != adapter)
                bi.recycler.adapter = adapter
        }
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(artist: PopularArtists.Artist){
        val i = Intent(applicationContext, DetailActivity::class.java)
        i.putExtra(DetailActivity.ARTIST, artist)
        startActivity(i)
    }
}