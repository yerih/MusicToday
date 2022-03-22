package com.architectcoders.musictoday.ui.main

import androidx.lifecycle.*
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel() : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val popularArtists: PopularArtists? = null,
        val navigateTo: PopularArtists.Artist? = null,
        val requestPermissionLocation: Boolean = true
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onUiReady(){
        viewModelScope.launch{
            _state.value = UiState(loading = true)
            _state.value = UiState(popularArtists = MusicService.service.getPopularArtists())
        }
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T { return MainViewModel() as T }
}
