package com.architectcoders.musictoday.ui.detail

import androidx.lifecycle.*
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.model.ArtistInfo
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.log
import com.architectcoders.musictoday.toLocalArtist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(artist: ArtistEntity): ViewModel() {

    data class UiState(
        val artist:        ArtistEntity? = null,
        val artistInfo:    ArtistInfo.Artist? = null
    )

    private val _state = MutableStateFlow(UiState(artist))
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onUiReady(artist: ArtistEntity?){
        _state.value = _state.value.copy(artist = artist)
    }

    fun onFavoriteClicked() {
        _state.value = _state.value.copy(artist = )
    }

}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val artist: ArtistEntity) :
ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(artist) as T
    }
}


