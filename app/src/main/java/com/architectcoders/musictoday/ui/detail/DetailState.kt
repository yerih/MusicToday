package com.architectcoders.musictoday.ui.detail

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.model.ArtistInfo
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.toLocalArtist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildDetailState() = DetailState(lifecycleScope)

class DetailState(
    private val scope: CoroutineScope,
) {

    fun onArtistInfoRequested(requestedArtist: ArtistEntity, onGetArtistInfo: (ArtistEntity) -> Unit){
        scope.launch {
            val artist = MusicService.service.getArtistInfo(requestedArtist.name).artist
                .toLocalArtist(requestedArtist.imageUrl)
            onGetArtistInfo(artist)
        }
    }

}