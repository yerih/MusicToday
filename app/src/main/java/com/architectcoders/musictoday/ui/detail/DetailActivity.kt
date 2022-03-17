package com.architectcoders.musictoday.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.architectcoders.musictoday.databinding.ActivityDetailsBinding
import com.architectcoders.musictoday.model.*
import kotlinx.coroutines.launch

class DetailActivity: AppCompatActivity() {
    companion object{
        const val ARTIST = "DetailActivity:artist"
    }

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(intent.getParcelableExtra(ARTIST)!!)
    }
    private lateinit var bi: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(bi.root)
        viewModel.state.observe(this, ::updateUI)
    }

    private fun updateUI(state: DetailViewModel.UiState){
        with(bi) {
            state.artistInfo?.let {
                artistDetailToolbar.title = it.name
                artistDetailSummary.text = it.bio.published
                artistDetailInfo.text = it.bio.summary
            }
            state.popularArtist?.let { artistDetailImage.loadUrl(it.picture_medium) }
        }
    }
}