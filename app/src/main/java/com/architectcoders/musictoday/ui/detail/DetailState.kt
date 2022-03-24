package com.architectcoders.musictoday.ui.detail

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.architectcoders.musictoday.model.ArtistInfo
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildDetailState() = DetailState(lifecycleScope)

class DetailState(
    private val scope: CoroutineScope,
) {

    fun onArtistInfoRequested(requestedArtist: PopularArtists.Artist, onGetArtistInfo: (ArtistInfo.Artist) -> Unit){
        scope.launch {
            val artistInfo = MusicService.service.getArtistInfo(requestedArtist.name).artist
            onGetArtistInfo(artistInfo)
        }
    }

}