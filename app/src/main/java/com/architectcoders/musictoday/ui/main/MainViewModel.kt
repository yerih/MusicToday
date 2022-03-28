package com.architectcoders.musictoday.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.log
import com.architectcoders.musictoday.ui.common.LocationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainViewModel(
    private val artistRepository: ArtistRepository
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val artists: List<ArtistEntity>? = null,
        val artistsByLocation: ArtistsByLocation.TopArtists? = null,
        val navigateTo: PopularArtists.Artist? = null,
        val requestPermissionLocation: Boolean = true
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            artistRepository.artists.collect { _state.value = UiState(artists = it) }
        }
    }

    fun onUiReady(){
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            artistRepository.getArtists()
        }
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: ArtistRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    { return MainViewModel(repository) as T }
}
