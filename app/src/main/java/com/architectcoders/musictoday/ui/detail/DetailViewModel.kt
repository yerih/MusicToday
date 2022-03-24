package com.architectcoders.musictoday.ui.detail

import androidx.lifecycle.*
import com.architectcoders.musictoday.model.ArtistInfo
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(popularArtist: PopularArtists.Artist): ViewModel() {

    data class UiState(
        val popularArtist: PopularArtists.Artist? = null,
        val artistInfo:    ArtistInfo.Artist? = null
    )

    private val _state = MutableStateFlow(UiState(popularArtist))
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onUiReady(artistInfo: ArtistInfo.Artist?){
        _state.value = _state.value.copy(artistInfo = artistInfo)
    }

}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val artist: PopularArtists.Artist) :
ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(artist) as T
    }
}


