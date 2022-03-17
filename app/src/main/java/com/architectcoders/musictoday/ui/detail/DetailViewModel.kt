package com.architectcoders.musictoday.ui.detail

import androidx.lifecycle.*
import com.architectcoders.musictoday.model.ArtistInfo
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import kotlinx.coroutines.launch

class DetailViewModel(popularArtist: PopularArtists.Artist): ViewModel() {

    data class UiState(
        val popularArtist: PopularArtists.Artist? = null,
        val artistInfo:    ArtistInfo.Artist? = null
    )

    private val _state = MutableLiveData(UiState(popularArtist))
    val state: LiveData<UiState> get() {
        if(_state.value?.artistInfo == null) refresh()
        return _state
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.value?.popularArtist?.let {
                val artistInfo = MusicService.service.getArtistInfo(it.name).artist
                _state.value = _state.value?.copy(artistInfo = artistInfo)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val artist: PopularArtists.Artist) :
ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(artist) as T
    }
}


