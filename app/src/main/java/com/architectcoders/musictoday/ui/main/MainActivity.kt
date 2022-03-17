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
import com.architectcoders.musictoday.model.log
import com.architectcoders.musictoday.ui.detail.DetailActivity
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels{MainViewModelFactory()}
    private val adapter = ArtistAdapter{ viewModel.onArtistClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bi.root)

//        bi.recycler.adapter = adapter
//        lifecycleScope.launch {
//            val artists = MusicService.service.getPopularArtists().artists
//            Log.i("TGB", "$artists")
//
//            val adapter = ArtistAdapter{ viewModel.onArtistClicked(it) }
//            adapter.artists = artists
//            bi.recycler.adapter = adapter
//        }
        viewModel.state.observe(this, ::updateUI)
    }

    private fun updateUI(state: MainViewModel.UiState) {
        //TODO: loader true
        log("updateUI: ${state.popularArtists}")
        state.popularArtists?.let {
            log("state popArts let")
            adapter.submitList(it.artists)
        }
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(artist: PopularArtists.Artist){
        val i = Intent(applicationContext, DetailActivity::class.java)
        i.putExtra(DetailActivity.ARTIST, artist)
        startActivity(i)
    }
}