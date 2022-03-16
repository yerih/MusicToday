package com.architectcoders.musictoday.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.architectcoders.musictoday.databinding.ActivityDetailsBinding
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.loadUrl
import com.architectcoders.musictoday.model.log
import kotlinx.coroutines.launch

class DetailActivity: AppCompatActivity() {
    companion object{
        const val ARTIST = "DetailActivity:artist"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bi = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(bi.root)

        intent.getParcelableExtra<PopularArtists.Artist>(ARTIST)?.run{
//            log("TGB", "run")
            lifecycleScope.launch {
                val artistInfo = MusicService.service.getArtistInfo(name).artist
                log("artistInfo = $artistInfo")
                bi.artistDetailToolbar.title = artistInfo.name
                bi.artistDetailSummary.text  = artistInfo.bio.published
                bi.artistDetailInfo.text     = artistInfo.bio.summary
                bi.artistDetailImage.loadUrl(picture_medium)
            }
        }
    }

}